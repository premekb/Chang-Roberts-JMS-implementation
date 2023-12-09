Prepis to synchronne nejspis

Udelej novy election message.
To bude delayed election message. Pri zpracovavani bude nejaky random delay od 500 ms do 1000 ms.
Tohleto zapnes na vicero nodech.

Predej shared variable novemu leaderovi
- Pri regularnim electionu kdyz soucasny leader procesuje ElectedMessage, tak to posli novemu leaderovi. DONE
- Pri logoutu zapni novy election. Pokud logout zapl soucasny leader, tak at neparticipuje a pouze forwardne. DONE

Pokud se zrovna votuje a uz je jiny leader. Ale nekdo to posle na stareho leadera, tak ten by to mel forwardnout.