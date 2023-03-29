# AdatbA_bookstore
Egy OracleDB-alapú könyvesbolt applikáció.

# Adatbázis alapú rendszerek
2022-2023/2  
IB152L-6

# Online könyvesbolt

## Készítette:
Bertók Laura  
Csizi Gergő Lajos  
Oláh Balázs

# Telepítés/beüzemelés

1. A 'java/com/bookstore/bookstore/sshconfig.java' fájlban meg kell adni az ssh felhasználót és a hozzá tartozó jelszót. (h-s azonosító, ahhoz tartozó jelszó)
2. A 'resources/hibernate.cfg.xml' fájlban a schema, username és password mezőkbe meg kell adni a hallgató Neptun kódját 'C##' kezdéssel.
3. Ugyanitt a 'hbm2ddl.auto' propertyt át kell írni 'create'-re. (Ez fogja létrehozni a táblákat az adatbázisban.)
4. El kell indítani az alkalmazást, majd amint elindult bezárni (MainController.java a belépési pont).
5. A 'resources/hibernateInsert.sql'-ben található inicializáló scriptet le kell futtatni SQLDeveloper-ben.
6. A 'hbm2ddl.auto' propertyt vissza kell írni 'validate'-re. (Így nem fogja újra kitörölni/létrehozni a táblákat újra és újra).
7. Működőképes a program, csak futtatni kell és használni.

