# Progetto di Programmazione di Sistemi Embedded 6CFU <br> App di Messaggistica Istantanea - CHATTO
## di Mattia Cozza <br> matricola: 2032576
<img src="/app/src/main/ic_launcher-playstore.png" alt="drawing" width="200"/>

### Specifiche dispositivo utilizzato 
<p>Nome Modello: Samsung Galaxy A50 <br>
Codice Modello: SM-A505FN/DS <br>
Versione Android: 11
</p>

### Librerie utilizzate
<p> In questo progetto ho utilizzato il framework Android Jetpack Compose per l'implementazione dell'interfaccia utente e la libreria HILT per l'iniezione delle dipendenze </p>

### Scelte implementative
<p> In aggiunta ai requisiti minimi del progetto ho implementato una funzione di Login, la quale permette all'utente d'inserire il suo numero di telefono al primo avvio dell'app. <br> 
Per gestire la corretta navigazione ho implementato un componente chiamato Dispatcher che implementa una sorta di navigazione condizionata, ovvero: <br>
- nel caso l'utente abbia inserito correttamente il numero, esso viene salvato nel database locale come profilo e l'utente viene così indirizzato alla schermata delle chat, garantendo così che al prossimo avvio dell'app non si riapra la schermata di Login <br>
- in alternativa l'utente rimane alla schermata Login per inserire un numero di telefono valido. <br>
Tutta la navigazione all'interno dell'app è gestita con il Navigation component di Jetpack Compose. <br>
Inoltre le chat sono dotate di un'immagine avatar casuale che simuli quella di un vero destinatario dei messaggi. <br>
La generazione dei messaggi è gestita come scelta casuale da una lista di messaggi. <br>
</p>

