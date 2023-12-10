# Zadaní
LE Ch-Ro Java-JMS shared-variable

# Jak spustit
Zapnout imqbrokerd, https://javaee.github.io/openmq/.

Localhost broker 
```
java -jar artifact.jar NODE_ID NODE_NAME NODE_ID_TO_LOGIN_TO NODE_NAME_TO_LOGIN_TO
java -jar artifact.jar 1 Alpha 2 Beta
```

Broker jinde než localhost
```
java -jar artifact.jar NODE_ID NODE_NAME NODE_ID_TO_LOGIN_TO NODE_NAME_TO_LOGIN_TO BROKER_IP_ADDRESS
java -jar artifact.jar 1 Alpha 2 Beta 192.168.64.2
```

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
queue s názvem “ID_NODU JMENO_NODU”, pokud queue už existuje tak ji vypurguje. V této queue pak poslouchá příchozí messages.

# Propojování uzlů
Nový uzel musí znát ID a jméno již běžícího uzlu. Při startu pošle do queue s názvem "ID_NODU JMENO_NODU" message
s žádostí o připojení. Potom čeká na příchozí message ve své queue s jeho sousedy.

# Oprava topologie
## Odhlášení
Node zapne novou volbu, které už se ale neúčastní, pouze forwarduje messages. Po zvolení nového leadera mu pošle
obsah proměnné a ostatním uzlům pošle nové sousedy.

## Vypnutí bez odhlášení
Každý node se periodicky dotazuje svého nexta, zda-li běží. Pokud node v nějakém časovém intervalu neodpoví, tak
tazatel začne s opravou topologie.