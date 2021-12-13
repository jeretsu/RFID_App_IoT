import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
import firebase_admin
from firebase_admin import credentials, db, firestore
import time

cred = credentials.Certificate('rfid-server-cred.json')
defeault_app = firebase_admin.initialize_app(cred)
db = firestore.client()

reader = SimpleMFRC522()
status = 0

print("Ready for RFID")

try:
    while True:
        id, text = reader.read()
        print("Card read")
        print(text)
        if status == 0:
            print("Present")
            status = 1
            db.collection('RFIDactions').document('RFIDdatabase').update({
                'Status' : 'Present',
                'Name' : text
                })
        else:
            print("Absent")
            status = 0
            db.collection('RFIDactions').document('RFIDdatabase').update({
                'Status' : 'Absent',
                'Name' : text})
        time.sleep(2)
except KeyboardInterrupt:
    GPIO.cleanup()
        