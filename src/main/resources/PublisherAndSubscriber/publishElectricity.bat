@echo off
cd C:\Program Files\mosquitto

:start_publish
    SET /A "whole=%random% %% 20+15"
    SET /A "decimal=%random% %% 9+0"
    mosquitto_pub -h 192.168.178.22 -t apartment%1/electricity/ -m %whole%.%decimal%
    timeout /T 1
    goto :start_publish