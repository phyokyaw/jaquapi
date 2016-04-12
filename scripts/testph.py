#!/usr/bin/env python2.7  

import RPi.GPIO as GPIO  
import paho.mqtt.client as mqtt
import spidev
import time
from os import listdir
from os.path import isfile

GPIO.setmode(GPIO.BCM)

ph_analog_channel = 0

# Open SPI bus
spi = spidev.SpiDev()
spi.open(0,0)


def publish_ph():
    adc = spi.xfer2([1,(8+ph_analog_channel)<<4,0])
    data = ((adc[1]&3) << 8) + adc[2]
    return data

i = 0
result = 0 
while (i < 40):
    result = result + publish_ph()
    i = i + 1
    time.sleep(0.2)

result = 3.5 * ((result * 5.0) / float(1023))
print round(result / 40, 2)

