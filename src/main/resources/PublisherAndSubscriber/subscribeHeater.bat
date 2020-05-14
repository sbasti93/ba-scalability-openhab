@echo off
cd C:\Program Files\mosquitto

:start_publish
    mosquitto_sub -h 192.168.178.67 -t apartment%1/heater/setNewLevel/
    goto :start_publish
