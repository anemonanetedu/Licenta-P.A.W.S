#include <SPI.h>
#include <MFRC522.h>

#include <Stepper.h>

#include <SoftwareSerial.h>

#include <EEPROM.h>

SoftwareSerial BTserial(A0, A1); // RX | TX Modul BT

const int stepsPerRevolution = 2038;
 
#define SS_PIN 10
#define RST_PIN 7
 
MFRC522 rfid(SS_PIN, RST_PIN);

Stepper myStepper = Stepper(stepsPerRevolution, 5, 7, 6, 8);

String uid = "";

struct pet{
  int uid1[4]; //UID tag
  int food; //Cantitatea de mancare
  unsigned long lastFedTimestamp; //Ultima hranire (se initializeaza mereu cu 0)
  unsigned long feedInterval; //Intervalul de hranire (in secunde)
};

pet pet1 = {{67,104,226,27}, 2, 0, 10}; //Adaugarea unui nou animal cu valorile specifice
pet pet2 = {{206,181,144,238}, 4, 0, 20};
 
MFRC522::MIFARE_Key key; 
 
void setup() { 
  Serial.begin(9600); //Initializare comunicare serial USB
  BTserial.begin(9600); //Initializare comunicare serial cu modulul BT
  SPI.begin();
  rfid.PCD_Init(); //Initializare modul RFID


  //Citire valori din memoria EEPROM
  unsigned long lastFed;
  EEPROM.get(0, lastFed);
  if(lastFed != 0){
    pet1.lastFedTimestamp = lastFed;
  }
  EEPROM.get(4, lastFed);
  if(lastFed != 0){
    pet2.lastFedTimestamp = lastFed;
  }
}
 
void loop() {

   if (Serial.available())
 {
    BTserial.write(Serial.read());
 }

  //Cod executat la primirea unei instructiuni prin BT
  if (BTserial.available())
 {
    byte petBT = BTserial.read();
    
    if(petBT==1)
      moveMotor(pet1.food);
    else if(petBT==2)
      moveMotor(pet2.food);
 }
 
  if ( ! rfid.PICC_IsNewCardPresent())
    return;
 
  if ( ! rfid.PICC_ReadCardSerial())
    return;
 
  MFRC522::PICC_Type piccType = rfid.PICC_GetType(rfid.uid.sak);
 
  Serial.print(F("RFID Tag UID:"));
  printHex(rfid.uid.uidByte, rfid.uid.size);
  
  int currentPet = verifyUID(rfid.uid.uidByte, rfid.uid.size);

  unsigned long lastFed;

  //Cod executat la recunoasterea unui tag RFID
  if(currentPet==1){
    if(millis() - pet1.lastFedTimestamp >= pet1.feedInterval * 1000){ //Verifica daca ultima hranire a fost mai recenta decat numarul de secunde predefinit
      moveMotor(pet1.food);
      pet1.lastFedTimestamp = millis();
      lastFed = pet1.lastFedTimestamp;
      EEPROM.put(0, lastFed);
    } else {
      Serial.println("Pet 1 was fed too recently.");
    }
    } else if(currentPet==2){
    if(millis() - pet2.lastFedTimestamp >= pet2.feedInterval * 1000){ //Verifica daca ultima hranire a fost mai recenta decat numarul de secunde predefinit
      moveMotor(pet2.food);
      pet2.lastFedTimestamp = millis();
      lastFed = pet2.lastFedTimestamp;
      EEPROM.put(4, lastFed);
    }else {
      Serial.println("Pet 2 was fed too recently.");
    }
  }
   
  Serial.println("");
 
  rfid.PICC_HaltA();
}

//Functie pentru printarea unei valori HEX
void printHex(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    Serial.print(buffer[i] < 0x10 ? " 0" : " ");
    Serial.print(buffer[i], HEX);
    Serial.print(" ");
    Serial.print(buffer[i]);
    Serial.print(" ");
    Serial.println(pet2.uid1[i]);
  }
}

//Functie pentru compararea UID-urilor
int verifyUID(byte *buffer, byte bufferSize) {
  int ok_final = 0;
  int ok = 0;
  for (byte i = 0; i < bufferSize; i++) {
    if(buffer[i] == pet1.uid1[i]) {
      ok = 1;
    }else
      ok = 0;
  }
  if(ok!=0)
    ok_final = ok;

  for (byte i = 0; i < bufferSize; i++) {
    if(buffer[i] == pet2.uid1[i]) {
      ok = 2;
    } else 
      ok=0;
  }

  if(ok!=0)
    ok_final = ok;

  if(ok_final==1)
    Serial.println("UID Pet 1");
  else if(ok_final==2)
    Serial.println("UID Pet 2");
  else
    Serial.println("UID-ul NU se potriveste!");
  return ok_final;
}

//Functie pentru actionarea motorului pentru o anumita cantitate de mancare
void moveMotor(int food){
  myStepper.setSpeed(16);
  myStepper.step(stepsPerRevolution/2*food);
  delay(1500);
}