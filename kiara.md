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
      - [Anwendung](#anwendung-7)
    - [Substitute Algorithm](#substitute-algorithm)
      - [Problem](#problem-8)
      - [Lösung](#lösung-8)
      - [Code](#code-8)
        - [Hier schlechten Code schreiben](#hier-schlechten-code-schreiben-8)
        - [Hier guten Code schreiben](#hier-guten-code-schreiben-8)
      - [Wieso anwenden?](#wieso-anwenden-8)
      - [Vorteile](#vorteile-8)
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



#### Lösung



#### Code

##### Hier schlechten Code schreiben

Wende [Replace Temp with Query](#replace-temp-with-query) an...

##### Hier guten Code schreiben

#### Wieso anwenden?


#### Vorteile



#### Anwendung

1. 

---


### Split Temporary Variable

#### Problem



#### Lösung



#### Code

##### Hier schlechten Code schreiben

Wende [](#) an...

##### Hier guten Code schreiben

#### Wieso anwenden?


#### Vorteile



#### Anwendung

1. 

---


### Remove Assignments to Parameters

#### Problem



#### Lösung



#### Code

##### Hier schlechten Code schreiben

Wende [](#) an...

##### Hier guten Code schreiben

#### Wieso anwenden?


#### Vorteile



#### Anwendung

1. 

---


### Replace Method with Method Object

#### Problem



#### Lösung



#### Code

##### Hier schlechten Code schreiben

Wende [](#) an...

##### Hier guten Code schreiben

#### Wieso anwenden?


#### Vorteile



#### Anwendung

1. 

---


### Substitute Algorithm

#### Problem



#### Lösung



#### Code

##### Hier schlechten Code schreiben

Wende [](#) an...

##### Hier guten Code schreiben

#### Wieso anwenden?


#### Vorteile



#### Anwendung

1. 

---
