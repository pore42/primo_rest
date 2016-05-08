# INGEGNERIA/PROGETTAZIONE DEL SOFTWARE — Laboratorio 8

Obiettivo del laboratorio è sviluppare una sempice applicazione
*client/server* di tipo *REST* che consenta di tener traccia di un insieme
di elaborazioni caratterizzate da un *nome* e da un orario di *inizio* e
*fine*, calcolando alcune statistiche di interesse sui tempi di esecuzione.

Questo laboratorio è propedeutico al progetto finale, che verterà in parte su
una applicazione REST.

## Processo

Questo laboratorio si svolge in **gruppi di quattro** studenti (suddivisi in
due **coppie da due** studenti ciascuna), il risultato *definitivo* del lavoro
va consegnato entro martedì 17 maggio alle ore 18:00.

Ogni gruppo di quattro studenti sceglie il **leader** che procede a [formare
il gruppo](https://sweng.di.unimi.it/gruppo) e ciascuno studente *clona* il
**fork del leader** di questo repository sulla propria macchina usando il
comando `git clone` riportato alla creazione del gruppo *(in nessun caso deve
essere clonato questo repository, ma solo il fork creato nell'account del
leader)*.

Una volta clonato il repository, il gruppo si *divide in coppie* e ciascuna
coppia implementa secondo la metodologia del TDD una delle due *parti* delle
specifiche riportate di seguito; in maggior dettaglio, dopo aver concordato
con l'altra coppia le modalità di divisione ed organizzzione del lavoro, ogni
coppia ripete i passi seguenti fino ad aver implementato, in modo
**indipendente** dall'altra, tutte le funzionalità richieste dalla parte scelta:

* sceglie un **breve** (indicativamente di 15m) passo di sviluppo,
* scrive il codice di un *test* per il passo scelto, verificando che **il
  codice compili correttamente**, ma l'**esecuzione del test fallisca**;
  se lo ritiene utile (ossia *facoltativamente*, ma non prima di trovarsi
  in queste condizioni) effettua un *commit* (usando `git add` e `git commit`)
  iniziando il messaggio di commit con la stringa `ROSSO:`,
* aggiunge l'implementazione necessaria a realizzare il passo relativo al test
  introdotto, in modo che **il test esegua con successo**; a questo punto *deve*
  effettuare un *commit* (usando `git add` e `git commit`) iniziando il messaggio di
  commit con la stringa `VERDE:`,
* procede, se necessario, al **refactoring** del codice, accertandosi che le modifiche
  non comportino il fallimento di alcun test; solo in questo caso fa seguire ad ogni
  passo di questo tipo un *commit* (usando `git add` e `git commit`) iniziando il
  messaggio di commit con la stringa `REFACTORING:`,
* effettua un *push* dei passi svolti su Bitbucket con `git push origin master`.

Al termine di questa fase, le due coppie **integrano** il loro lavoro, mettendo
le classi scritte indipendentemente in grado di cooperare; a tale scopo
possono aggiungere eventuali test di integrazione (procedendo in maniera
analoga alla prima fase di sviluppo).

Al termine del periodo di lavoro il gruppo effettua un ultimo *push* e
**verifica su Bitbucket** che ci sia la completa traccia di *commit*
effettuati. Si suggerisce di eseguire i test non soltanto con Eclipse, ma
anche eseguendo il comando `./gradlew build` da riga di comando.

## Applicazioni REST

Una applicazione
[REST](https://en.wikipedia.org/wiki/Representational_state_transfer)
è una applicazione *client/server* usualmente basata sul protocollo
[HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol)
che può *molto informalmente* essere descritta come un insieme di **azioni** indicate da
[verbi HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Request_methods)
(come GET, POST, PUT, DELETE…) che agiscono su delle **risorse**, identificate da
[URI](https://en.wikipedia.org/wiki/Uniform_Resource_Identifier)
e solitamente rappresentate in formato [JSON](https://en.wikipedia.org/wiki/JSON).

Ad esempio [Pokéapi](http://pokeapi.co/) "The RESTful Pokémon API" è una
applicazione REST che conente di avere informazioni sui Pokémon. Poiché si
basa su un protocollo standard è possibile utilizzarla con un comune *client*
HTTP, come ad esempio [curl](https://curl.haxx.se/); se vogliamo conoscere le
informazioni sul primo Pokémon possiamo eseguire il seguente comando nella
*shell*
```bash
$ curl -sL -X GET http://pokeapi.co/api/v2/pokemon/1/
```
ottenendo in risposta una lunghissima serie di dati che iniza con
```json
{
  "id": 1,
  "name": "bulbasaur",
  "base_experience": 64,
  "height": 7,
  "is_default": true,
  "order": 1,
  "weight": 69,
  "abilities": [
    {
      "is_hidden": true,
      "slot": 3,
      "ability": {
        "name": "chlorophyll",
        "url": "http://pokeapi.co/api/v2/ability/34/"
      }
    },
    {
      "is_hidden": false,
      "slot": 1,
      "ability": {
        "name": "overgrow",
        "url": "http://pokeapi.co/api/v2/ability/65/"
      }
    }
  ],
```

La *richiesta*, eseguita dal *client*, è caratterizzata da:

* il verbo `GET`,
* l'URI `http://pokeapi.co/api/v2/pokemon/1/` che può essere divisa in:
    * `http://pokeapi.co/api/v2` che è l'*endpoint* delle API,
    * `/pokemon/1/` che identifica la *risorsa* `pokemon` e, in fine,
    * dall'identificativo della specifica entità `1`.

La *risposta*, restituita dal *server*, è un documento testuale che adotta il
formato JSON per codificare vari tipi di dato come: stringhe, numeri, o tipi
composti come liste di valori, o mappe chiave-valore.

Ovviamente, è possibile interrogare un *server* REST non soltanto con *client*
"generici" (a linea di comando), ma spesso vengono realizzati *client* dedicati;
le [Google Maps](https://www.google.com/maps), ad esempio, sono un *client* web
che utilzza le [Google Maps APIs](https://developers.google.com/maps/) che sono
per l'appunto un esempio di applicazione REST (si vedano, in particolare le
[Google Maps Web Service APIs](https://developers.google.com/maps/web-services/)).

### L'applicazione di esempio

Il *package* [it.unimi.di.sweng.lab08.example](/src/main/java/it/unimi/di/sweng/lab08/example/)
contiene un applicazione REST costituita da un *server* (contenuto in
[it.unimi.di.sweng.lab08.example.server](/src/main/java/it/unimi/di/sweng/lab08/example/server/))
e da un *client* dedicato (contenuto in
[it.unimi.di.sweng.lab08.example.client](/src/main/java/it/unimi/di/sweng/lab08/example/client/)). L'applicazione offre diversi *endpoint* in grado di "salutare" e di gestire "cibo" e "bevande".

Per eseguire il *server* è sufficiente (una volta compilato il progetto con
`./gradlew assemble`) eseguire il comando
```bash
$ ./server.sh
```
che è uno script che esegue la classe [it.unimi.di.sweng.lab08.example.server.
Server](/src/main/java/it/unimi/di/sweng/lab08/example/server/Server) con
l'opportuno *classpath*; in alternativa, è possibile assegnare alla variabile
d'ambiente `CLASSPATH` l'opportuno valore con il comando
```bash
$ source ./set_classpath.sh
```
dopodiché il *server* può essere posto in esecuzione con l'usuale comando
```bash
$ java it.unimi.di.sweng.lab08.example.server.Server
```

Il *server* può essere esercitato con `curl` ad esempio come segue
```bash
$ curl -sL -X GET http://localhost:8080/g/greet/friend
Hi friend!
$ curl -sL -X GET http://localhost:8080/g/greet/James
Hi James!
$ curl -sL -X GET http://localhost:8080/ed/foods
{"Lettuce":4,"Bacon":3,"Tomato":6,"Bread":5}
$ curl -sL -X POST http://localhost:8080/ed/eat/Bread
$ curl -sL -X POST http://localhost:8080/ed/eat/Dog
{"code":404, …, "description":"The food Dog is not available.",…}
$ curl -sL -X GET http://localhost:8080/ed/beverages
["Water","Wine","Beer"]
$ curl -sL -X POST http://localhost:8080/ed/drink/Beer
```

Similmente, si può porre in esercizio il *client* ad esempio come segue
```bash
$ ./client.sh foods
Starting the internal HTTP client
There are 4 items of Lettuce
There are 3 items of Bacon
There are 6 items of Tomato
There are 4 items of Bread
$ ./client.sh eat Tomato
Starting the internal HTTP client
./client.sh beverages
Starting the internal HTTP client
Beverages: [Water, Wine, Beer]
$ ./client.sh drink Water
Starting the internal HTTP client
```
oppure (una volta definita la variabile `CLASSPATH` come sopra), con
```bash
$ java it.unimi.di.sweng.lab08.example.client.Client eat Bacon
Starting the internal HTTP client
$ java it.unimi.di.sweng.lab08.example.client.Client beverages
Starting the internal HTTP client
Beverages: [Water, Wine, Beer]
$ java it.unimi.di.sweng.lab08.example.client.Client foods
Starting the internal HTTP client
There are 4 items of Lettuce
There are 2 items of Bacon
There are 5 items of Tomato
There are 4 items of Bread
```

#### Il codice d'esempio

Il codice sviluppato dal gruppo deve **risiedere fuori** dal *package*
[it.unimi.di.sweng.lab08.example](/src/main/java/it/unimi/di/sweng/lab08/example/)
il cui codice **non deve essere modificato** in nessun modo.

Viceversa, il gruppo è caldamente invitato a *studiare* il codice d'esempio e
ad adoperarlo liberamente,  sia *adattandone* e copiandone parti nella propria
implementazione, che *usandolo* (istanziandone le classi ed invocandone i
metodi).

Si osservi che il codice d'esempio comprende anche dei *test* (contenuti in
[it.unimi.di.sweng.lab08.example](/src/test/java/it/unimi/di/sweng/lab08/)),
e delle implementazioni *mock* (contenute in
[it.unimi.di.sweng.lab08.example.mock](/src/test/java/it/unimi/di/sweng/lab08/example/mock/))
che possono essere molto utili durante le fasi indipendendi di sviluppo.

## Il framework Restlet

L'applicazione di esempio (così come dovrà essere per quella che è oggetto di
questo laboratorio) usa il [Restlet Framework](https://restlet.com/projects/restlet-framework/)
che consente di scrivere sia il lato *server* che quello *client*
dell'applicazione facendo uso di API di alto livello che
"nascondono" al programmatore (tra l'altro) una serie di complesse questioni legate alla
concorrenza, alla gestione dei protocolli di rete ed alla codifica e decodifica del formato JSON.

### Il server

Un *server* in Restlet può contenere più applicazioni (come
avviene in [Server](/src/main/java/it/unimi/di/sweng/lab08/example/server/Server.java)),
ciascuna delle quali è implementata estendendo
[org.restlet.Application](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jee/api/org/restlet/Application.html)
e definendo un opportuno
[org.restlet.routing.Router](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/routing/Router.html)
che associa le URI con le risorse
(come fanno ad esempio
[GreetApplication](/src/main/java/it/unimi/di/sweng/lab08/example/server/GreetApplication.java) e
[EatAndDrinkApplication](/src/main/java/it/unimi/di/sweng/lab08/example/server/EatAndDrinkApplication.java)); a loro volta le risrorse sono implementate estendendo
[org.restlet.resource.ServerResource](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jee/api/org/restlet/resource/ServerResource.html),
e definiscono come reagire alle richieste HTTP tramite le annotazioni
[org.restlet.resource.Get](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/Get.html),
[org.restlet.resource.Post](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/Post.html)…
(come accade ad esempio in [GreetResource](/src/main/java/it/unimi/di/sweng/lab08/example/server/GreetResource.java), o
[FoodsResource](/src/main/java/it/unimi/di/sweng/lab08/example/server/FoodsResource.java)).

Si osservi in particolare che nelle [ServerResource](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jee/api/org/restlet/resource/ServerResource.html):

* le URI possono contenere una sorta di **parametri**, ossia parti variabili delimitate da `{}`,
  che le risorse possono ottenere invocando il metodo [getAttribute](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jee/api/org/restlet/resource/ServerResource.html#getAttribute(java.lang.String))
* nelle annotazioni dei verbi è possibile specificare il formato con cui il **valore di ritorno** sarà
  automaticamente codificato dal server (ad esempio con `@Get("json")`).

Questo consente, per così dire, di assimilare in buona sostanza la *richiesta di una risorsa* ad una *chiamata di funzione*.

#### Persistenza e concorrenza

Non c'è garanzia che un *server* Restlet gestisca le richieste istanziando una sola
volta le classi coinvolte e le adoperi in modo [thread safe](https://en.wikipedia.org/wiki/Thread_safety):
per loro natura le applicazioni REST sono concorrenti e per ragioni di scalabilità
può persino accadere che il *server* sia posto in esecuzione su più di una JVM, che
non è garantito restino in esecuzione per la durata di vita dell'applicazoine.

Per questa ragione, le informazioni necessarie all'applicazione **non possono
essere banalmente memorizzate nei membri delle classi coinvolte**, ma è
usualmente richiesto un approccio molto più sofisticato, spesso basato su un
databse esterno all'aplicazione (in grado di  garantire sia la *persistenza*
dei dati in caso di riavvio della JVM, che la possiblità di accessi
concorrenti, da più *thread* della stessa JVM, o addirittura da *thread* di
JVM distinte).

Il framework Restlet offre diverse implementazioni concrete per i *server*;
nel caso dell'implementazione usata nell'applicazione di
esempio, si ha almeno la garanzia che tutte le classi coinvolte convivano in
una stessa JVM. Per questa ragione, un approccio plausibile è adoperare un
[singleton](https://en.wikipedia.org/wiki/Singleton_pattern) per gestire
in modo semplice almeno la memorizzazione *thread safe* dei dati (sebbene non la loro perisstenza).

Pertanto, **per gestire la memorizzazione delle informazioni si consiglia di usare
l'implementazione del *singleton* basata sulle `enum`** di Java descritta in
["Effective Java"](https://goo.gl/5NnAn0), di Joshua Bloch, avendo cura di proteggere
i metodi pubblici con [`syncronized`](https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html).

Si può avere un esempio di tale approccio nelle classi del *package*
[it.unimi.di.sweng.lab08.example.model](/src/main/java/it/unimi/di/sweng/lab08/example/server/).

### Il client

Un client Restlet è definito a partire da una
[org.restlet.resource.ClientResource](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/ClientResource.html)
che consente di "interrogare" il *server*

* **direttamente** con vari verbi, usando ad esempio i metodi
  [get](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/ClientResource.html#get()),
  [post](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/ClientResource.html#post(java.lang.Object))…
  ottenendo in rispotsa una
  [org.restlet.representation.Representation](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/representation/Representation.html)
  dalla quale si può ad esempio ottenere il testo della risposta col metodo
  [getText](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/representation/Representation.html#getText()),

* oppure avvolgendo col metodo
  [wrap](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/ClientResource.html#wrap(java.lang.Class))
  una interfaccia *opportunamente annotata* con
  [@Get](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/Get.html),
  [@Post](https://restlet.com/technical-resources/restlet-framework/javadocs/2.3/jse/api/org/restlet/resource/Post.html)…,
  ottenendo in questo modo la **decodifica automatica** della risposta resituita dal server
  in opportune strutture dati Java.

Un esempio del primo approccio è contenuto in [MockClient](/laboratorio08_2016-M/src/test/java/it/unimi/di/sweng/lab08/mock/MockClient.java),
mentre un approcio del secondo è contenuto in [Client](/laboratorio08_2016-M/src/main/java/it/unimi/di/sweng/lab08/example/client/Client.java)
che, ad esempio, usa l'interfaccia annotata [FoodsResource](/laboratorio08_2016-M/src/main/java/it/unimi/di/sweng/lab08/example/client/FoodsResource.java)
per trasformare la risposta (in fomrato JSON) del server in un oggetto di tipo
[Map<String,Integer>](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html).

## Specifiche

L'applicazione REST da sviluppare deve consentire di tener traccia di un
insieme di elaborazioni (che avvengono nell'arco di una sola giornata) ed
effettuare alcune statistiche sui loro tempi di esecuzione.

Ogni *elaborazione* è caratterizzata da:

* un **nome** *unico* (composto da una sequenza di caratteri alfanumerici),
* un'ora di **inizio** (nel formato `HH:MM`, con `HH` che varia da `00` a `23`),
* un'ora di **fine** (nello stesso formato di quella di inizio).

si può assumere che nell'arco di una giornata ogni elaborazione avvenga una
sola volta.

### Server

Una **coppia** di due studenti deve occuparsi della realizzazione del *server*,
implementando *obbligatoriamente* la capacità di rispondere a queste richieste:

* `GET /jobs`, che restituisce l'elenco dei nomi delle elaborazioni note,
* `GET /job/{name}`, che restituisce gli orari di inizio e fine (se nota) dell'elaborazione di nome `name`,
* `POST /job/{name}/begin/{HH:MM}`, che registra l'inizio dell'elaborazione di nome `name` all'orario `HH:MM`,
* `POST /job/{name}/end/{HH:MM}`, che registra la fine dell'elaborazione di nome `name` all'orario `HH:MM`.

e *facoltativamente* alle richieste:

* `GET /running`, che restituisce l'elenco dei nomi delle elaborazioni per cui non sia stata segnalata la terminazione,
* `GET /active/{HH:MM}`, che restituisce l'elenco dei nomi delle elaborazioni in corso all'orario `HH:MM`.

### Client

Un'altra **coppia** di studenti deve occuparsi della realizzazione di (uno, o
più) *client*; le classi realizzate devono *obbligatoriamente*:

* implementare dei *metodi* per effettuare in *modo programmatico* tutti i tipi
  di richiesta cui il *server* è in grado di rispondere,

* cosentire di usare la *riga di comando* per esercitare tali metodi,
  emettendo sul flusso d'uscita standard una qualche rappresentazione
  "leggibile" delle informazioni ottenute (ossia non riportando
  testualmente il formato JSON restituito dal *server*),

inoltre, i *client* possono *facoltativamente* consentire di:

* calcolare (con opportuni metodi) alcune *statistiche* riguardo ai
  tempi di elaborazione (durata minima, massima, media, distribuzione
  delle durate…),

* consentire di esercitare tali metodi dalla riga di comando.

Si osserva, a titolo di suggerimento, che l'uso del pattern
[strategy](https://en.wikipedia.org/wiki/Strategy_pattern) potrebbe rivelarsi
particolarmente indicato per gestire le diverse statistiche da calcolare,
così come la [riflessività](https://docs.oracle.com/javase/tutorial/reflect/)
potrebbe essere un modo efficace (seppur complesso) di consentire di specificare
da riga di comando quale statistica si intenda ottenere.

### Integrazione

Il **gruppo** deve (sia in fase *preliminare*, che *al termine* delle fasi
indipendenti di programmazione) discutere e tener conto dei vari aspetti che
consentano al *server* ed ai *client* di interoperare in modo corretto;
viceversa l'interazione tra le coppie dev'essere **minima** (se non nulla),
durante lo sviluppo delle rispettive parti.

Alcune delle questioni su cui è bene che il gruppo lavori di concerto sono, a
titolo d'esempio:

* come specificare "parametri" e "valori restituiti", sia riguardo ai *tipi di
  dati* che ai *valori accettabili*, o corretti;
* come segnalare e gestire le condizioni di errore (escludendo
  quelle legate a malfuzionamenti di rete, che possono essere ignorate);
* come stabilire la correttezza delle rispettive parti (senza interagire
  durante lo sviluppo).

