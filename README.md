# Bridžový zápisník 

Aplikace pro zápis výsledků z jednotlivých bridžových rozdání a turnajů.

Zápočtový projekt do předmětu Programování Mobilních Aplikací na [MFF](https://mff.cuni.cz).

## Uživatelská dokumentace

Uživatelská dokumentace je dostupná v souboru [user-guide.md](user-guide.md).

## Programátorská dokumentace
- Prerekvizity
  - JDK 17
  - Android 2.0.21
  - Android SDK 34
  - Gradle 8.10.2
- Konfigurovatelné vložení demo v dat v souboru [build.gradle](./app/build.gradle.kts):
  ```kotlin
  android {
    defualtConfig {
          buildConfigField("boolean", "INSERT_DEMO_DATA", "true") // change to false for not inserting demo data
      }
  }
  ```

## Co je bridž

Bridž je karetní hra a sport. Více informací na [Wikipedii](https://cs.wikipedia.org/wiki/Brid%C5%BE).

## Autoři

- [Patrik Trefil](https://www.patriktrefil.com/)
- [Zdeněk Tomis](https://zdenektomis.eu)
- Ondřej Kaštovský
