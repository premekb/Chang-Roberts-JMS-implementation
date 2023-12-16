# Zadaní
LE Ch-Ro Java-JMS shared-variable

# Jak spustit
Zapnout imqbrokerd, https://javaee.github.io/openmq/.

Localhost broker 
```
java -jar artifact.jar NODE_ID NODE_NAME NODE_ID_TO_LOGIN_TO NODE_NAME_TO_LOGIN_TO [NO_HEARTBEAT_LOGS/WITH_HEARTBEAT/LOGS]
java -jar artifact.jar 1 Alpha 2 Beta NO_HEARTBEAT_LOGS
```

Broker jinde než localhost
```
java -jar artifact.jar NODE_ID NODE_NAME NODE_ID_TO_LOGIN_TO NODE_NAME_TO_LOGIN_TO BROKER_IP_ADDRESS [NO_HEARTBEAT_LOGS/WITH_HEARTBEAT/LOGS]
java -jar artifact.jar 1 Alpha 2 Beta 192.168.64.2 NO_HEARTBEAT_LOGS
```

2 brokeři v clusteru
```
java -jar artifact.jar NODE_ID NODE_NAME NODE_ID_TO_LOGIN_TO NODE_NAME_TO_LOGIN_TO BROKER_1_IP_ADDRESS BROKER_2_IP_ADDRESS [NO_HEARTBEAT_LOGS/WITH_HEARTBEAT/LOGS]
java -jar artifact.jar 1 Alpha 2 Beta 192.168.64.2 192.168.64.5 NO_HEARTBEAT_LOGS
```

Poslední argument určuje zda-li logovat periodické heartbeat message a trackovat u nich logický čas.
Doporučuju funkci vypnout, v opačném případě je velmi nepřehledné co systém dělá.

# Implementovaná funkcionalita
- ? - vypiš příkazy
- get - dostaň hodnotu proměnné
- set {content} - nastav hodnotu proměnné
- election - začni volby
- delection - začni volby, ve kterých jsou zprávy opožďovány o 4 sekundy
- status - vypiš sousedy a leadera
- topology - vypiš topologii
- logout - odhlaš se a informuj ostatní
- terminate - vypni se bez informování ostatních

# Topologie
Nody jsou uspořádány v kruhu. Topologii lze vypsat pomocí příkazu "topology". Výpis je ve formátu: \
node - next - next next - next next next - ...

# Data uzlu
- Vlastní adresa (= ID_NODU JMENO_NODU) a příslušný konzument
- Je-li node leader
- Hodnota proměnné pokud je leader
- Zda-li node volí
- Zda-li se node odhlašuje
- Logický čas
- Adresy nexta, nnexta, preva a leadera a příslušní producenti


# Příjem a odesílání zpráv
Na jednom uzlu běží broker, na který se ostatní specifikací IP adresy musí připojit. Každý node si při přihlášení vytvoří
queue s názvem “ID_NODU JMENO_NODU”, pokud queue už existuje tak ji vypurguje. V této queue pak poslouchá příchozí messages,
zpracovávání těchto zpráv probíha asynchronně.\
Každý node si drží 4 producenty na svoje sousedy a 1 konzumenta. Při potřebě poslat zprávu nodu, který není soused pak lze
vytvořit jednorázového producenta, který je poté zničen.

# Propojování uzlů
Nový uzel musí znát ID a jméno již běžícího uzlu. Při startu pošle do queue s názvem "ID_NODU JMENO_NODU" message
s žádostí o připojení. Potom čeká na příchozí message ve své queue s jeho sousedy. Uzel na který se připojuje upraví sousedy u sebe
a zároveň i sousedy ostatních, aby odpovádali novému stavu topologie.

# Oprava topologie
## Odhlášení
Node zapne novou volbu, které už se ale neúčastní, pouze forwarduje messages. Po zvolení nového leadera mu pošle
obsah proměnné a ostatním uzlům pošle nové sousedy a poté se vypne.

## Vypnutí bez odhlášení
Každý node se periodicky dotazuje svého nexta, zda-li běží. Pokud node v nějakém časovém intervalu neodpoví, tak
tazatel začne s opravou topologie a posílat ostatním uzlům nové sousedy. V případě, že neodpovídající node je leader jsou zapnuty volby a data sdílené proměnné jsou ztracena.

## Případy opravy pod 3 uzly
Od 3 uzlů navrch je vždy algoritmus opravy stejný (kdo má jaké nové sousedy). Pro konfiguraci 1, 2 a 3 uzlů se na základě ifu posílají noví sousedi
při opravě jinak.