@echo off

FOR /L %%d IN (1 1 %1) DO  (

    IF %2 EQU 0 (
        start /Min ../PublisherAndSubscriber/publishTemperature.bat %%d
    )


)
