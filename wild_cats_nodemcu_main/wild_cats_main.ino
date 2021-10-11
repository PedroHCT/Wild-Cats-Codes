
//libraries
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
#include <Ultrasonic.h>
#include <ESP8266HTTPClient.h>
#include <Servo.h>

//led RGB ports
#define R D4 
#define G D3
#define B D2

//ultrasonic sensor ports
#define usTrig D6
#define usEcho D7
//ultrasonic sensor variables
Ultrasonic ultrasonic(usTrig, usEcho);
int distance;
//servo variable
Servo servo;
//wifi variables
WiFiClient wifiClient;
HTTPClient http;


//---Global variables---
const String apiBaseUrl = "http://ec2-3-15-211-207.us-east-2.compute.amazonaws.com:8080";
const String localSensor = "NodeMCU - Corredor Principal - Ala Direita";
const char* ssid = "VIVOFIBRA-35EB";
const char* password = "34TVToiv4b";
String formattedMacAddress = "";
bool isAlreadyUsed = false;
int usedCount = 0;
int allUsedCount = 0;
bool wasStocked = false;
//quantidade de alcool em gel dispensado por uso em g
int fluidDispensedPerUse = 30;
//quantidade de alcool em gel que cabe no dispenser em g
int amountDispenserFluid = 520;


void setup() {
  
  //servo motor setup
  servo.attach(D5);
  servo.write(0);

  //led RGB setup
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
  analogWrite(R, 255);
  analogWrite(G, 255);
  analogWrite(B, 255);

  
  
  Serial.begin(9600);
  wifiConnection();
  
  String getDispResp = getDispenserInfos(formattedMacAddress);
  //verificar se o dispenser ja existe
  if(getDispResp == "error"){
    
      String dispenserInfos = createPayload(formattedMacAddress, usedCount, allUsedCount, wasStocked);
      createDispenser(dispenserInfos);
      
  }else{
    
      DynamicJsonDocument dispenserInfos(2048);
      
      deserializeJson(dispenserInfos,getDispResp);
      usedCount = dispenserInfos["dispenser"]["used"];
      allUsedCount = dispenserInfos["dispenser"]["allUsedCount"];
      
  }
 
  
  
}

//MAIN FUNCTION
void loop() {
    
    //get distance in CM
    Serial.print("isAlreadyUsed : ");
    Serial.println(isAlreadyUsed);
    distance = ultrasonic.read();
    // put your main code here, to run repeatedly:
    wasStocked = false;
    int fluidLevel = calculateFluidLevel(usedCount).toInt();
    int analogSensorValue = analogRead(A0);
    
    //turn on led RGB according to the fluidLevel
    if(fluidLevel > 0)
           turnOnLed(0,255,0);
    else
           turnOnLed(255,0,0);
        
    //check if have someone using the dispenser
    if(distance < 15){
          if(isAlreadyUsed == false){
                if(fluidLevel > 0){
                  
                  allUsedCount++;
                  usedCount++;
                  dispenseAlcohol();
                   
                }else{
                  blinkLed(255,0,0,3);
                  delay(1000);
                }

        }           
      }else{
          isAlreadyUsed = false;
        }
        
    
     if(analogSensorValue < 1024){
      
              Serial.println(analogSensorValue);
              usedCount = 0;
              wasStocked = true;
              int blueValue = 0;
              for(blueValue; blueValue < 255; blueValue = blueValue + 5){
                
                turnOnLed(0, 0, blueValue);
                delay(75);
              }

              blinkLed(0,0,255,3);
              String dispenserInfos = createPayload(formattedMacAddress, usedCount, allUsedCount, wasStocked);
              updateDispenser(dispenserInfos);
      
      } 

  keepConnections();
  delay(300);
}


//HELP FUNCTIONS

void dispenseAlcohol(){
  
    
    servo.write(180);
    blinkLed(0,255,0,3);
    //delay(1000);
    servo.write(0);
    String dispenserInfos = createPayload(formattedMacAddress, usedCount, allUsedCount, wasStocked);
    updateDispenser(dispenserInfos);

    isAlreadyUsed = true;

}

void turnOnLed(int redValue, int greenValue, int blueValue){
  
    //convert values to led
    redValue = 255 - redValue;
    greenValue = 255 - greenValue;
    blueValue = 255 - blueValue;
    analogWrite(R, redValue);
    analogWrite(G, greenValue);
    analogWrite(B, blueValue);
  
}

void blinkLed(int redValue, int greenValue, int blueValue, int blinkCount){
    //convert values to led
    redValue = 255 - redValue;
    greenValue = 255 - greenValue;
    blueValue = 255 - blueValue;
    for(blinkCount; blinkCount > 0; blinkCount--){
      
         analogWrite(R, redValue);
         analogWrite(G, greenValue);
         analogWrite(B, blueValue);
         delay(150);
         analogWrite(R, 255);
         analogWrite(G, 255);
         analogWrite(B, 255);
         delay(150);
         
     }

    analogWrite(R, redValue);
    analogWrite(G, greenValue);
    analogWrite(B, blueValue);

}

void keepConnections(){

    if(WiFi.status() != WL_CONNECTED){
     wifiConnection();
    }
}

void wifiConnection(){
  // We start by connecting to a WiFi network

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  /* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("WiFi connected");
  formattedMacAddress= WiFi.macAddress();
  Serial.println("MAC address : " + WiFi.macAddress());
  formattedMacAddress.replace(":","");
  
}

String createPayload(String formattedMacAddress, int usedCount, int allUsedCount, bool wasStocked){
  DynamicJsonDocument doc(256);

  doc["fluidLevel"] = calculateFluidLevel(usedCount);
  doc["local"] = localSensor;
  doc["macAddress"] = formattedMacAddress;
  doc["used"] = String(usedCount);
  doc["allUsedCount"] = String(allUsedCount);
  doc["stocked"] = wasStocked;
  
  
  String payload;
  serializeJsonPretty(doc, payload);
  Serial.println("Payload : ");
  Serial.println(payload);
  return payload;
}

String calculateFluidLevel(int usedCount){
    
  float fluidLevel = usedCount * fluidDispensedPerUse;
  fluidLevel = fluidLevel / amountDispenserFluid;
  fluidLevel = fluidLevel * 100;
  //Serial.println("Fluid Level : " + String(100 - fluidLevel) + " ~ " + String(round(100 - fluidLevel)));
  fluidLevel = round(100 - fluidLevel);
  int fluidLevelInteger = 0;
  if(fluidLevel > 0)
    fluidLevelInteger = fluidLevel;
  
  

  return String(fluidLevelInteger);
}

String getDispenserInfos(String formattedMacAddress){
  
  digitalWrite(D0, LOW);
  String url = apiBaseUrl + "/dispenser/mac/" + formattedMacAddress;
  http.begin(wifiClient, url);
  int httpCode = http.GET(); 
  String response = http.getString();
  httpLog("GET", url, httpCode, response);
  String ret = "";
  
    if (httpCode > 0) {     
      if(httpCode == 200)
        ret = response;
      else
        ret = "error";
    }else{
     
      ret = "error";
      
    }    
    //fechando a conexão
    http.end();
    digitalWrite(D0, HIGH);
    return ret;
  
}
void createDispenser(String dispenserInfos){
  
  digitalWrite(D0, LOW);
  String url = apiBaseUrl + "/dispenser/";
  http.begin(wifiClient, url);
  http.addHeader("Content-Type", "application/json");
  int httpCode = http.POST(dispenserInfos);
  String response = http.getString();
  httpLog("POST", url, httpCode, response);
      
  //fechando a conexão
  http.end();
  digitalWrite(D0, HIGH);
  
}

void updateDispenser(String dispenserInfos){
  
  digitalWrite(D0, LOW);
  String url = apiBaseUrl + "/dispenser/mac/" + formattedMacAddress;
  http.begin(wifiClient, url);
  http.addHeader("Content-Type", "application/json");
  int httpCode = http.PUT(dispenserInfos);
  String response = http.getString();
  httpLog("PUT", url, httpCode, response); 

  //fechando a conexão
  http.end();
  digitalWrite(D0, HIGH);
  
}

void httpLog(String httpMethod, String httpUrl, int httpCode, String httpResponse){

  Serial.println("HTTP " + httpMethod + " url : " + httpUrl);
  Serial.println("HTTP " + httpMethod + " code : " + httpCode);
  if(httpCode > 0)
    Serial.println("HTTP " + httpMethod + " response : " + httpResponse);
  else
     Serial.println("HTTP " + httpMethod +  " - ERROR");

}
