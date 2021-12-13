import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()

try:
    text = input("New Data:")
    print("Place Tag to Write")
    reader.write(text)
    print("Written")
finally:
    GPIO.cleanup()
