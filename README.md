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
<p> In aggiunta alla consegna del progetto ho inserito un'activity di Login che permetta all'utente d'inserire il suo numero di telefono al primo avvio dell'app, quindi salvando 
in un file di preferenze l'avvenuto login oppure il necessario inserimento del numero di telefono al prossimo avvio dell'app, <br> 
per gestire questa funzionalità l'app presenta un componente chiamato Dispatcher che si occupa della navigazione nell'activity login oppure home in seguito alla valutazione del file di preferences <br>
Tutta la navigazione all'interno dell'app è gestita con il Navigation component di Jetpack Compose<br>
Inoltre le chat sono dotate di un'immagine avatar casuale che simuli quello di un vero utente.<br>
La generazione dei messaggi è gestita come scelta casuale da una lista di messaggi.
</p>

