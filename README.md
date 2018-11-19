# test-app

This is my sample app,<br>
that shows use of MVVM patttern of Google architecture components.<br>
App do parse and shows currency rates of belarussian rouble.<br>
There are such main components:<br>
app View (activities, fragments),<br>
app View Model (uses google ViewModel and LiveData components) and<br>
Model (contains network components, DB, that combined in to a Repository module and rest of other code).<br>
Repository contains 3 different DB for storing data, which one to use can be switched by the app constant flag.<br>
If app loading currency rates from network then it will automatically stores to DB.<br>
When app goes offline, it uses stored currencies in DB.<br><br>
App uses such libraries and technologies:<br>
Android Architecture Components (View Model, Live Data);<br>
RxJava;<br>
Retrofit;<br>
DI: Dagger2;<br>
Xml parsing: Json, SimpleXml;<br>
DB: Realm, Room, SQLite;<br>
Testing: JUnit, Robolectric, Mockito.
