# PROJEKTRAPPORT K_Solo
## Deltagare: Kerim Kozo
### Beskrivning av projektet
Vi fick i uppgift att skapa en databas för att hålla koll på favoritfilmer. Databasen ska innehålla information om filmer, skådespelare, årtal, genre och regissörer. 
Man ska kunna söka efter filmer och skådespelare. Om filmen du söker inte finns i databasen ska du använda dig av OMDb API för att hämta information om filmen. 
Efter lyckad sökning ska filmen läggas till i databasen.

### Dependencies
Maven: com.github.gotson:sqlite-jdbc:3.32.3.8
Maven: org.json:json:20230227

### Vem har gjort vad?
Jag arbetade ensam

### Planering och genomförande
Arbetet började med att jag samlade mina tankar i ett word document, försökte implementera dem och avslutade med egna reflektioner t.ex
vad som behöver ändras. Mitt arbetssätt påminde om att arbeta agilt och detta arbetsätt skedde varje dag som arbetet pågick.

### Utmaningar & Exempel
Detta var första stora arbetet mot ett api & en databas vilket var en utmaning i sig. Jag började enkelt med att få nyckel, apiet och databasen till att fungera.
Därefter fokuserade jag på menyvalen. Det som jag fastnade ett tag på var ifall jag skulle söka på en film, spara ned den och kunna filtrera min sökning är jag söker i databasen
t.ex genom att söka på regissör eller ifall detta skulle ske i samband med att jag kallar på apiet. Som tur var så slapp jag det andra alternativet
vilket hade inneburit att min sökning ska ändra parametrarna i apiUrlen. Nu räckte det med att jag har "?api" + apikey + "&t=" + titel.


## Slutsatser
### Vad gick bra/dåligt & vad hade du kunnat göra annorlunda?
Projektet gick bra i stort sett. Det som var tufft var att få alla klasser till att fungera och prata med varandra.
Det som jag hade kunnat göra annorlunda då jag har sett hur andra har gjort är att skippa buffer & json klassen och istället baka in dem i API klassen.
Jag personligen föredrar att separera dem, det blir flera klasser men det blir tydligare, åtminstone för mig.

### Lärdommar & möjligheter
Projektet har gett mig insikten i att jobba mot ett api & en sql databas vilket var nytt för mig. Nu har jag lärt mig grunderna och kan 
själv utforska olika apier och spara ned informationen i en databas. Kunskapen har också gett mig en möjlighet att förstå hur man jobbar med sql
vilket ökar mina chanser att få en praktikplats / arbete på ett företag som dagligen arbetar med detta.
