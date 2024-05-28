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
<p> In aggiunta alla consegna del progetto ho implementato una funzione di Login che permetta all'utente d'inserire il suo numero di telefono al primo avvio dell'app, quindi per gestire la corretta navigazione ho implementato un componente chiamato dispatcher con il compito di osservare lo stato di una variabile che indica o meno l'avvenuto inserimento da parte dell'utente del suo numero di telefono, nel caso salvandolo nel database locale come profilo, la scelta di un oggetto profilo nel database è stata fatta anche per garantire una possibile estensione dell'app nel caso un'utente richiedesse di avere più numeri di telefono e per la creazione di una schermata profilo (estensione pensata ma non implementata causa  mancanza di tempo). <br>
Tutta la navigazione all'interno dell'app è gestita con il Navigation component di Jetpack Compose. <br>
Inoltre le chat sono dotate di un'immagine avatar casuale che simuli quella di un vero utente. <br>
La generazione dei messaggi è gestita come scelta casuale da una lista di messaggi. <br>
</p>

