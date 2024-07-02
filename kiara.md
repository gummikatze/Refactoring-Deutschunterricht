# Methods

Zum Verständnis wird in diesem Dokument immer von Methoden gesprochen. Alles was auf Methoden zutrifft, trifft zugleich auf Funktionen zu.

## Table of Contents

- [Methods](#methods)
  - [Table of Contents](#table-of-contents)
  - [Composing Methods](#composing-methods)
    - [Extract Method](#extract-method)
      - [Problem](#problem)
      - [Lösung](#lösung)
      - [Code](#code)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben)
      - [Wieso anwenden?](#wieso-anwenden)
      - [Vorteile](#vorteile)
      - [Anwendung](#anwendung)
    - [Inline Method](#inline-method)
      - [Problem](#problem-1)
      - [Lösung](#lösung-1)
      - [Code](#code-1)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-1)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-1)
      - [Wieso anwenden?](#wieso-anwenden-1)
      - [Vorteile](#vorteile-1)
      - [Anwendung](#anwendung-1)
    - [Extract Variable](#extract-variable)
      - [Problem](#problem-2)
      - [Lösung](#lösung-2)
      - [Code](#code-2)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-2)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-2)
      - [Wieso anwenden?](#wieso-anwenden-2)
      - [Vorteile](#vorteile-2)
      - [Nachteile](#nachteile)
      - [Anwendung](#anwendung-2)
    - [Inline Temp](#inline-temp)
      - [Problem](#problem-3)
      - [Lösung](#lösung-3)
      - [Code](#code-3)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-3)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-3)
      - [Wieso anwenden?](#wieso-anwenden-3)
      - [Vorteile](#vorteile-3)
      - [Nachteile](#nachteile-1)
      - [Anwendung](#anwendung-3)
    - [Replace Temp with Query](#replace-temp-with-query)
      - [Problem](#problem-4)
      - [Lösung](#lösung-4)
      - [Code](#code-4)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-4)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-4)
      - [Wieso anwenden?](#wieso-anwenden-4)
      - [Vorteile](#vorteile-4)
      - [Anwendung](#anwendung-4)
    - [Split Temporary Variable](#split-temporary-variable)
      - [Problem](#problem-5)
      - [Lösung](#lösung-5)
      - [Code](#code-5)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-5)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-5)
      - [Wieso anwenden?](#wieso-anwenden-5)
      - [Vorteile](#vorteile-5)
      - [Anwendung](#anwendung-5)
    - [Remove Assignments to Parameters](#remove-assignments-to-parameters)
      - [Problem](#problem-6)
      - [Lösung](#lösung-6)
      - [Code](#code-6)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-6)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-6)
      - [Wieso anwenden?](#wieso-anwenden-6)
      - [Vorteile](#vorteile-6)
      - [Anwendung](#anwendung-6)
    - [Replace Method with Method Object](#replace-method-with-method-object)
      - [Problem](#problem-7)
      - [Lösung](#lösung-7)
      - [Code](#code-7)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-7)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-7)
      - [Wieso anwenden?](#wieso-anwenden-7)
      - [Vorteile](#vorteile-7)
      - [Nachteile](#nachteile-2)
      - [Anwendung](#anwendung-7)
    - [Substitute Algorithm](#substitute-algorithm)
      - [Problem](#problem-8)
      - [Lösung](#lösung-8)
      - [Code](#code-8)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-8)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-8)
      - [Wieso anwenden?](#wieso-anwenden-8)
      - [Anwendung](#anwendung-8)

## Composing Methods

### Extract Method

#### Problem

Du hast ein Code-Fragment, welches du zu einer Methode zusammenfassen kannst.

#### Lösung

Schiebe den Code in eine neue separate Methode und ersetze den Code an der alten Stelle durch einen Methoden-Call.

#### Code

##### Hier schlechten Code schreiben

Wende [Extract Method](#extract-method) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

Desto mehr Zeilen in einer Methode stehen, desto schwieriger wird es rauszufinden, was diese Methode überhaupt macht.

#### Vorteile

- Methodennamen können Code bereits beschreiben
- Weniger Code Duplikate
- Code-Isolierung
- Fehler können im Stacktrace leichter gefunden werden

#### Anwendung

1. Erstelle eine neue Methode mit einem selbstbeschreibenden Namen.
2. Kopiere das relevante Code-Fragment in deine neue Methode. Lösche das Fragment an seiner ursprünglichen Position und ersetze es mit dem Aufruf der Methode.
3. Wenn du Variablen verwendest, die vorher im Code definiert wurden, musst du diese als Parameter hinzufügen. Manchmal hilft es auch, die [Replace Temp with Query](#replace-temp-with-query) Methode zu verwenden.
4. Wenn du bemerkst, dass eine lokale Variable in deinem Code sich verändert, könnte dies darauf hinweisen, dass du den veränderten Wert später benötigst. Falls dies der Fall ist, Füge `return` zu deiner extrahierten Methode hinzu.

---

### Inline Method

#### Problem

Eine Methode wird für etwas verwendet, was ohne die Methode schneller erreicht werden kann.

#### Lösung

Ersetze den Aufruf der Methode mit dem Inhalt der Methode und lösche diese.

#### Code

##### Hier schlechten Code schreiben

Wende [Inline Method](#inline-method) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

Eine Methode, die eine andere Methode aufruft, ist grundsätzlich kein Problem. Aber wenn sich dieses System zu tief verschachtelt, geht der Überblick schnell verloren.

Oftmals sind Methoden zunächst nicht so kurz, dass man diese mithilfe der [Inline Method](#inline-method) ersetzen kann. Ändert man jedoch das Programm ab, sollte man sich auch immer überlegen, ob es diese Methode wirklich braucht.

#### Vorteile

- Reduziert Anzahl an Methoden
- Macht Code einfacher zu lesen

#### Anwendung

1. Stelle sicher dass die Methode nicht in irgendwelchen Subklassen neu definiert wird. Falls die Methode neu definiert wurde, solltest du diese Technik nicht verwenden.
2. Finde alle Aufrufe dieser Methode. Ersetze sie mit dem Inhalt der Methode.
3. Lösche diese Methode.

---

### Extract Variable

#### Problem

Du hast einen schwer verständlichen Ausdruck.

#### Lösung

Setze das Ergebnis, oder Teile dessen, in einzelne Variablen, die sich selbst erklären können.

#### Code

##### Hier schlechten Code schreiben

Wende [Extract Variable](#extract-variable) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

Der Hauptnutzen einer extrahierten Variable ist es, einen komplexen Ausdruck leichter verständlich zu machen, indem man ihn in mehrere kleinere Schritte aufteilt. Diese könnten sein:

- Boolean-Variablen (isIdle)
- Ein langer, arithmetischer Ausdruck ohne Zwischenergebnis

Eine extrahierte Variable könnte auch der erste Schritt zur [Extract Method](#extract-method), falls der extrahierte Ausdruck öfters im Code vorkommt.

#### Vorteile

- leichter lesbarer Code
- weniger Comments
- kürzere Zeilen

#### Nachteile

- mehr Variablen im Code <-> Einfacheres Lesen
- bedenke, dass der Compiler konditionale Ausdrücke `if a() || b() ...` optimiert und die Anzahl an notwendigen Berechnungen minimiert. In diesem Fall wird `b` nicht ausgeführt, falls `a` `true` zurückgibt. Wenn wir jedoch die Ausdrücke in Variablen speichern, werden beide Methoden immer aufgerufen, was wiederum zu Performance-Einbußen führen kann.

#### Anwendung

1. Deklariere eine neue Variable vor dem relevanten Ausdruck. Gebe dieser Variable einen beschreibenden Namen.
2. Gebe dieser Variable den gewünschten Teil des komplexen Ausdrucks.
3. Tausche den Teil des relevanten Ausdrucks mit der Variable.
4. Wiederhole den Prozess so oft wie nötig.

---

### Inline Temp

#### Problem

Eine temporäre Variable speichert nur den Wert eines simplen Ausdrucks.

#### Lösung

Ersetze die Variable durch den Wert selbst.

#### Code

##### Hier schlechten Code schreiben

Wende [Inline Temp](#inline-temp) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

Lokale Variablen werden oft als Teil der [Replace Temp with Query](#replace-temp-with-query) oder [Extract Method](#extract-method) verwendet und vergessen.

#### Vorteile

- Verbessert Lesbarkeit des Programms, falls die Variable nur eine Methode aufruft

#### Nachteile

- Manchmal sind scheinbar sinnlose Variablen da, um das Ergebnis eines aufwendigen Ausdrucks zu speichern, der mehrfach verwendet wird

#### Anwendung

1. Finde alle Stellen im Code, die diese Variable nutzen.
2. Ersetze die Variable durch einen Aufruf der Methode.
3. Lösche die Variable.

---

### Replace Temp with Query

#### Problem

Das Ergebnis eines Ausdrucks wird in einer lokalen Variable platziert, um später weiterverwendet zu werden.

#### Lösung

Schiebe den Ausdruck in eine separate Methode und gib das Ergebnis zurück. Rufe die Methode auf anstelle der Variable.

#### Code

##### Hier schlechten Code schreiben

Wende [Replace Temp with Query](#replace-temp-with-query) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

- Der selbe Ausdruck könnte in verschiedenen Methoden vorkommen.
- Könnte als Grundlage für [Extract Method](#extract-method) dienen.

#### Vorteile

- Es ist einfacher `getBrutto()` zu verstehen, anstelle von `nettoPrice() * 1.19`
- Ermöglicht Wiederverwendung von Ergebnissen

#### Anwendung

1. Stelle sicher dass der Wert der Variable nur einmalig zugewiesen wird.
2. Nutze die [Extract Method](#extract-method), um den Ausdruck in eine Methode zu packen. Stelle sicher dass sich nur der Wert, nicht der Zustand des Objekts ändert. (Nur Getter, kein Setter)
3. Ersetze die Variable mit dem Aufruf der neuen Methode.

---

### Split Temporary Variable

#### Problem

Es wird eine lokale Variable `temp` verwendet, um Zwischenergebnisse innerhalb einer Methode zu speichern.

#### Lösung

Nutze verschiedene Variablen für verschiedene Werte. Jede Variable sollte nur eine Sache darstellen.

#### Code

##### Hier schlechten Code schreiben

Wende [Split Temporary Variable](#split-temporary-variable) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

Wenn Änderungen an einer Methode gemacht werden müssen, die von `temp`-Variablen abhängig ist, müsste immer wieder überprüft werden, in welchem Kontext `temp` gerade steht.

#### Vorteile

- Jeder Teil eines Programms sollte immer nur für genau eine Sache verantwortlich sein. Dadurch wird es einfacher, Code zu bearbeiten, ohne Angst vor ungewollten Änderungen zu haben.
- Der Code wird leichter lesbar. Beim Programmieren werden öfters simple Variablennamen wie `v`, `rs` etc. verwendet. Diese Namen sind nicht selbstbeschreibend. Stattdessen sollten Namen wie `carVelocity` oder `rotationSpeed` verwendet werden.

#### Anwendung

1. Finde zunächst den Anfang der temporären Variable. Benenne diese entsprechend dem Wert, der ihr zugewiesen wird.
2. Nutze den neuen Namen an den jeweils relevanten Erstellen.
3. Wiederhole den Prozess so oft wie nötig

---

### Remove Assignments to Parameters

#### Problem

Einem Parameter wird innerhalb einer Methode ein Wert zugewiesen.

#### Lösung

Nutze eine lokale Variable anstelle des Parameters.

#### Code

##### Hier schlechten Code schreiben

Wende [Remove Assignments to Parameters](#remove-assignments-to-parameters) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

Die Gründe für diese Methode sind die gleichen wie für [Split Temporary Variable](#split-temporary-variable), nur handelt es sich hierbei um einen Parameter statt einer lokalen Variable.

#### Vorteile

- Jeder Teil eines Programms sollte immer nur für genau eine Sache verantwortlich sein. Dadurch wird es einfacher, Code zu bearbeiten, ohne Angst vor ungewollten Änderungen zu haben.

#### Anwendung

1. Erstelle eine lokale Variable und übergib ihr den Wert des Parameter.
2. Ersetze die Aufrufe des Parameters durch die Variable.

---

### Replace Method with Method Object

#### Problem

Eine sehr lange Methode besitzt Variablen, die so stark miteinander verwurzelt sind, dass die [Extract Method](#extract-method) nicht anwendbar ist.

#### Lösung

Ändere die methode in eine eigene Klasse um, wodurch lokale Variablen durch Klassenfelder ersetzt werden. Dann kann die Methode in mehrere Kleinere aufgeteilt werden.

#### Code

##### Hier schlechten Code schreiben

Wende [Replace Method with Method Object](#replace-method-with-method-object) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

Eine Methode ist so lang und so kompliziert, dass es schwierig ist, alles voneinander zu trennen. Die Methode in einer Klasse unterzubringen hilft dabei, den Code zu isolieren

#### Vorteile

- Verhindern, dass die Methode noch weiter aufgebläht wird.
- Ermöglicht es, die Methode in weitere kleinere Methoden aufzuteilen, ohne die Hauptklasse mit Hilfsklassen weiter zu belasten.

#### Nachteile

- Eine weitere Klasse wird hinzugefügt, wodurch das Programm komplexer wird.

#### Anwendung

1. Erstelle eine neue Klasse. Benenne sie basierend auf den Nutzen der Methode, die refaktorisiert wird.
2. In der neuen Klasse, erstelle ein privates Feld mit einer Referenz zu der Klasse, in der die Methode vorher aufzufinden war.
3. Erstelle ein weiteres privates Feld für jede lokale Variable der Methode.
4. Erstelle einen Konstruktor, welcher alle Werte als Parameter nimmt.
5. Erstelle eine neue Methode und kopiere den Originalcode der vorherigen Methode hinein. Ersetze alle lokalen Variablen durch Klassenfelder.
6. Erstelle ein neues Objekt der neuen Klasse und rufe die neue Methode anstelle der Vorherigen auf.

---

### Substitute Algorithm

#### Problem

Ein Algorithmus soll ersetzt werden.

#### Lösung

Ersetze den alten Algorithmus durch den Neuen.

#### Code

##### Hier schlechten Code schreiben

Wende [Substitute Algorithm](#substitute-algorithm) an...

##### Hier guten Code schreiben

#### Wieso anwenden?

1. Stufenweises Refaktorisieren ist nicht der einzige Weg, ein Programm zu verbessern. Manchmal ist eine Methode so bepackt mit Problemen, dass ein Neuanfang einfach die beste Option ist. Im besten Fall findet man hierbei einen optimierteren Algorithmus, der simpler und/oder effizienter ist.
2. Die Anforderungen an das Programm ändern sich so stark, dass der bestehende Algorithmus nicht weiterverwendet werden kann.

#### Anwendung

1. Stelle sicher, dass der bestehende Algorithmus so stark vereinfacht ist, wie nur möglich. Entferne unwichtige Teile mithilfe der [Extract Method](#extract-method). Desto kleiner der Algorithmus, desto leichter ist es, diesen zu ersetzen.
2. Erstelle einen neuen Algorithmus in einer neuen Methode. Ersetze den alten Algorithmus durch den neuen und teste das Programm.
3. Wenn die Ergebnisse nicht übereinstimmen, wechsle zur vorherigen Implementierung und vergleiche die Ergebnisse. Versuche, den Grund für den Unterschied festzustellen. Es könnte sein, dass es entweder zu einem Fehler im vorherigen Algorithmus kam, oder dass der neue Algorithmus noch nicht ausgereift ist.
4. Wenn alle Tests korrekt ablaufen, ersetze den alten Algorithmus endgültig.
