# Zápisník bridžových výsledků

## Hlavní funkce aplikace:

- Zápis výsledků z jednotlivých rozdání: Uživatelé zapisují struktrované údaje,
  jako například následující:
  - Číslo rozdání
  - Soupeři
  - Posazení
  - Vydražený závazek
  - Dosažený výsledek
  - Výnos
  - Vlastní textová pozvánka
- Strukturování do turnajů: Uživatelé mohou vytvářet turnaje, do kterých
  zapisují jednotlivá rozdání. Turnaje obsahují následující informace:
  - Název
  - Datum
  - Odkaz na výsledky
  - Vlastní poznámku
  - S kým uživatel hrál - tým či pár

## Wireframe

Následující obrázky zobrazují wireframe aplikace.

Wireframe lze také spustit jako ukázku (tzv. Figma prototype). Pro spuštění přejděte na [tento odkaz](https://www.figma.com/proto/k3PSpzlAXuX3KFgIziJnJN/bridge-notes?node-id=2-2&t=LZRXga6sTH0gn94S-1). Není vyžádováno žádné přihlášení, aplikace běží v prohlížeči. Klikáním na tlačítka se můžete pohybovat mezi jednotlivými obrazovkami.

![all wireframes](img/wireframes.png)

<div id="imgs" style="display:flex; flex-direction: row; gap: 1em; flex-wrap: wrap;" markdown="1">

![alt text](img/deal-add.png) ![alt text](img/deal-detail.png)
![alt text](img/tournament-create.png) ![alt text](img/tournament-detail.png)
![alt text](img/tournament-edit.png) ![alt text](img/tournamentlist-search.png)
![alt text](img/tournament-list.png)

</div>

Přechody mezi jednotlivými obrazovkami jsou následující:

1. Z hlavní obrazovky je seznam turnajů.
1. Z turnajů je možné přejít na detail turnaje.
1. Z detailu turnaje je možné přejít na detail rozdání.
1. Z detailu rozdání je možné přejít na editaci rozdání.
1. Z editace rozdání je možné přejít zpět na detail rozdání.
1. Z detailu rozdání je možné přejít zpět na detail turnaje.
1. Z detailu turnaje je možné přejít na editaci turnaje.
1. Z editace turnaje je možné přejít zpět na detail turnaje.
1. Z detailu turnaje je možné přejít zpět na seznam turnajů.
1. Z hlavní obrazovky je možné přejít na přidání nového turnaje.
1. Z přidání nového turnaje je možné přejít zpět na hlavní obrazovku.
1. Z detailu turnaje je možné přejít na přidání nového rozdání.
1. Z přidání nového rozdání je možné přejít zpět na detail turnaje.
1. Z detailu rozdání je možné přejít na přidání nového rozdání.
