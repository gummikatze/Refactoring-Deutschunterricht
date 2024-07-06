# Refactoring

## Extract method

### Vorher

```java
public void inventar() {
    if (inventaropen == 0) {
        System.out.println("");
        System.out.println("");
        System.out.println("   Inventar geöffnet!");
        System.out.println("   Diese Sachen befinden sich in ihrem Inventar: ");
        for (int i = 0; i < inventar.size(); i++) {
            System.out.print(inventar.get(i));
            if (i < inventar.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("");
        System.out.println(
                "   Schreib den Namen des Objektes um es auszuwählen oder 'exit' um das Inventar zu verlassen");
        System.out.print("> ");
        command = sc.nextLine().toLowerCase();
        switch (command) {
            case "stab":
                if (stab == 1) {
                    System.out.println("   Stab als Waffe ausgerüstet!");
                    stabequip = 1;
                    schwertequip = 0;
                    inventar();
                } else {
                    System.out.println("   Ungültiger Befehl!");
                    inventar();
                }
            case "exit":
                inventaropen = 1;
                inventar();
            case "schwert":
                if (schwert == 1) {
                    System.out.println("   Schwert als Waffe ausgerüstet");
                    schwertequip = 1;
                    stabequip = 0;
                    inventar();
                } else {
                    System.out.println("   Ungültiger Befehl!");
                    inventar();
                }
            case "energydrink":
                if (energyDrink >= 1) {
                    playerHP = playerHP + 5;
                    System.out.println("   Du hast den Energydrink getrunken dich um 5HP geheilt!");
                    energyDrink--;
                    inventar.remove("energydrink");
                    inventar();
                } else {
                    System.out.println("   Du hast kein Energy mehr du Junkie!");
                    inventar();
                }
            case "stein":
                if (stein == 1) {
                    System.out.println("   Ein Stein. Was hast du erwartet?");
                    inventar();
                } else {
                    System.out.println("   Ungültiger Befehl");
                }
                inventar();
            case "heiltrank":
                if (heiltrank >= 1) {
                    System.out.println("   Heiltrank getrunken! Du wurdest geheilt!");
                    playerHP = playerHP + new java.util.Random().nextInt(10) + 5;
                    heiltrank--;
                    if (heiltrank == 0) {
                        inventar.remove("Heiltrank");
                        inventar();
                    } else {
                        inventar();
                    }
                } else {
                    System.out.println("   Du hast keinen Heiltrank mehr!");
                    inventar();
                }
            case "halskette":
                System.out.println("   Die Halskette ist wunderschön. (Sie erhöht deine Abwehrchance).");
                inventar();
            default: System.out.println("   Falsche Eingabe!");
            inventar();
        }
    } else {
        inventaropen = 0;
        System.out.println("   Inventar verlassen!");
        playerInput();
    }

}
```

### Nachher

```java
public void inventar() {
    if (inventaropen == 0) {
        openInventar();
        handleCommand();
    } else {
        closeInventar();
    }
}


private void openInventar() {
    System.out.println("\n\n   Inventar geöffnet!");
    System.out.println("   Diese Sachen befinden sich in ihrem Inventar: ");
    displayInventarItems();
    System.out.println("\n   Schreib den Namen des Objektes um es auszuwählen oder 'exit' um das Inventar zu verlassen");
    System.out.print("> ");
}

private void closeInventar() {
    inventaropen = 0;
    System.out.println("   Inventar verlassen!");
    playerInput();
}

private void displayInventarItems() {
    for (int i = 0; i < inventar.size(); i++) {
        System.out.print(inventar.get(i));
        if (i < inventar.size() - 1) {
            System.out.print(", ");
        }
    }
}

private void handleCommand() {
    command = sc.nextLine().toLowerCase();
    switch (command) {
        case "stab":
            equipStab();
            break;
        case "exit":
            exitInventar();
            break;
        case "schwert":
            equipSchwert();
            break;
        case "energydrink":
            drinkEnergy();
            break;
        case "stein":
            inspectStein();
            break;
        case "heiltrank":
            drinkHeiltrank();
            break;
        case "halskette":
            inspectHalskette();
            break;
        default:
            invalidCommand();
    }
}
    
    private void equipStab() {
        if (stab == 1) {
            System.out.println("   Stab als Waffe ausgerüstet!");
            stabequip = 1;
            schwertequip = 0;
        } else {
            System.out.println("   Ungültiger Befehl!");
        }
        inventar();
    }
    
    private void exitInventar() {
        inventaropen = 1;
        inventar();
    }
    
    private void equipSchwert() {
        if (schwert == 1) {
            System.out.println("   Schwert als Waffe ausgerüstet");
            schwertequip = 1;
            stabequip = 0;
        } else {
            System.out.println("   Ungültiger Befehl!");
        }
        inventar();
    }
    
    private void drinkEnergy() {
        if (energyDrink >= 1) {
            playerHP += 5;
            System.out.println("   Du hast den Energydrink getrunken dich um 5HP geheilt!");
            energyDrink--;
            inventar.remove("energydrink");
        } else {
            System.out.println("   Du hast kein Energy mehr du Junkie!");
        }
        inventar();
    }
    
    private void inspectStein() {
        if (stein == 1) {
            System.out.println("   Ein Stein. Was hast du erwartet?");
        } else {
            System.out.println("   Ungültiger Befehl");
        }
        inventar();
    }
    
    private void drinkHeiltrank() {
        if (heiltrank >= 1) {
            System.out.println("   Heiltrank getrunken! Du wurdest geheilt!");
            playerHP += new java.util.Random().nextInt(10) + 5;
            heiltrank--;
            if (heiltrank == 0) {
                inventar.remove("Heiltrank");
            }
        } else {
            System.out.println("   Du hast keinen Heiltrank mehr!");
        }
        inventar();
    }
    
    private void inspectHalskette() {
        System.out.println("   Die Halskette ist wunderschön. (Sie erhöht deine Abwehrchance).");
        inventar();
    }
    
    private void invalidCommand() {
        System.out.println("   Falsche Eingabe!");
        inventar();
    }
```

### Ablauf

Ich habe jede action die mit dem Inventar zu tun hat in eine eigene methode gesteckt.
Dadurch ist es übersichtlicher und einfacher zu verwalten oder zu verändern.

## Inline method

### vorher

```java
 public static void main(String[] args) {
        new Game().start();
    }

   //ausgeblendeter code

 public void start() {
        playerSetup();
    }
```

### Nachher

```java
public static void main(String[] args) {
        new Game().playerSetup();
    }
```

### Ablauf

Ich habe die start methode komplett entfernt da der einzige nutzen von der war, das die playersetup methode aufgerufen wird.
Damit es weiterhin funktioniert, habe ich im main den aufruf der start methode mit dem aufruf der playersetup methode geändert.

## Extract Variable

### Vorher

```java
case ("feefight"):
    if (monsterHP > 0) {
        System.out.println("   Die Fee ist dran!");
        monsterHitAC = new java.util.Random().nextInt(20) + 1;
        if (monsterHitAC > playerAC) {
            if (monsterHitAC > 15) {
                monsterdmg = new java.util.Random().nextInt(4) + 2;
                playerHP = playerHP - monsterdmg;
                System.out.println("   Die Fee fügt dir " + monsterdmg + " Schaden zu!");
            } else {
                playerHP = playerHP / 2;
                System.out.println(
                        "   Die Fee benutzt ihren Zauber 'AUSWEIDUNG DER STERBLICHEN' und fügt dir "
                                + playerHP + " Schaden zu!");
            }
```

### Nachher

```java
 case ("feefight"):
    if (monsterHP > 0) {
        System.out.println("   Die Fee ist dran!");
        monsterHitAC = new java.util.Random().nextInt(20) + 1;
        if (monsterHitAC > playerAC) {
            if (monsterHitAC > 15) {
                monsterdmg = new java.util.Random().nextInt(4) + 2;
                playerHP = playerHP - monsterdmg;
                System.out.println("   Die Fee fügt dir " + monsterdmg + " Schaden zu!");
            } else {
                monsterdmg = playerHP /2;
                playerHP = playerHP - monsterdmg;
                System.out.println(
                        "   Die Fee benutzt ihren Zauber 'AUSWEIDUNG DER STERBLICHEN' und fügt dir "
                                + monsterdmg + " Schaden zu!");
            }
```

### Ablauf

in dem code wurde bei dem angriff der deine leben um 50% verringert die playerhp als monsterdmg genutzt
ich habe den monsterdmg berechnen berechnen lassen das es auch sinn ergibt das der monsterdmg existiert.

## Replace temp with query

### Vorher

```java
public void monsterAttack() {
    switch (position) {
        case ("goblinfight"):
            if (monsterHP > 0) {
                System.out.println("   Der Goblin ist dran!");
                monsterHitAC = new java.util.Random().nextInt(20);
                if (monsterHitAC > playerAC) {
                    monsterdmg = new java.util.Random().nextInt(4) + 2;
                    playerHP = playerHP - monsterdmg;
                    System.out.println("   Der Goblin fügt dir " + monsterdmg + " Schaden zu!");
                } else {
                    System.out.println("   Der Goblin hat bei seinem Angriff verfehlt!");
                }
            } else {
                position = "";
                System.out.println(
                        "   Du hast den Goblin besiegt! \n   Der Goblin hat einen Energy Drink und ein Schwert gedroppt!");
                inventar.add("energydrink");
                inventar.add("schwert");
                schwert = 1;
                energyDrink++;
                labyrinth23();
            }
```

### Nachher

```java
Random random = new Random();

    private int getRandomNum(int limit, int base) {
        return random.nextInt(limit) + base;

    }

    //ausgeblendeter code

    public void monsterAttack() {
        switch (position) {
            case ("goblinfight"):
                if (monsterHP > 0) {
                    System.out.println("   Der Goblin ist dran!");
                    monsterHitAC = getRandomNum(20, 0);
                    if (monsterHitAC > playerAC) {
                        monsterdmg = getRandomNum(4, 2);
                        playerHP = playerHP - monsterdmg;
                        System.out.println("   Der Goblin fügt dir " + monsterdmg + " Schaden zu!");
                    } else {
                        System.out.println("   Der Goblin hat bei seinem Angriff verfehlt!");
                    }
                } else {
                    position = "";
                    System.out.println(
                            "   Du hast den Goblin besiegt! \n   Der Goblin hat einen Energy Drink und ein Schwert gedroppt!");
                    inventar.add("energydrink");
                    inventar.add("schwert");
                    schwert = 1;
                    energyDrink++;
                    labyrinth23();
```

### Ablauf

In dem code wurde merhfach eine neue random variable erstellt.
Dementsprechend habe ich eine methode erstellt die bei einer benötigten random zahle eine Random zahl zurückgibtz.

## Replace method with method object

### Vorher

```java
public void inventar() {
    if (inventaropen == 0) {
        openInventar();
        handleCommand();
    } else {
        closeInventar();
    }
}


private void openInventar() {
    System.out.println("\n\n   Inventar geöffnet!");
    System.out.println("   Diese Sachen befinden sich in ihrem Inventar: ");
    displayInventarItems();
    System.out.println("\n   Schreib den Namen des Objektes um es auszuwählen oder 'exit' um das Inventar zu verlassen");
    System.out.print("> ");
}

private void closeInventar() {
    inventaropen = 0;
    System.out.println("   Inventar verlassen!");
    playerInput();
}

private void displayInventarItems() {
    for (int i = 0; i < inventar.size(); i++) {
        System.out.print(inventar.get(i));
        if (i < inventar.size() - 1) {
            System.out.print(", ");
        }
    }
}

private void handleCommand() {
    command = sc.nextLine().toLowerCase();
    switch (command) {
        case "stab":
            equipStab();
            break;
        case "exit":
            exitInventar();
            break;
        case "schwert":
            equipSchwert();
            break;
        case "energydrink":
            drinkEnergy();
            break;
        case "stein":
            inspectStein();
            break;
        case "heiltrank":
            drinkHeiltrank();
            break;
        case "halskette":
            inspectHalskette();
            break;
        default:
            invalidCommand();
    }
}
    
    private void equipStab() {
        if (stab == 1) {
            System.out.println("   Stab als Waffe ausgerüstet!");
            stabequip = 1;
            schwertequip = 0;
        } else {
            System.out.println("   Ungültiger Befehl!");
        }
        inventar();
    }
    
    private void exitInventar() {
        inventaropen = 1;
        inventar();
    }
    
    private void equipSchwert() {
        if (schwert == 1) {
            System.out.println("   Schwert als Waffe ausgerüstet");
            schwertequip = 1;
            stabequip = 0;
        } else {
            System.out.println("   Ungültiger Befehl!");
        }
        inventar();
    }
    
    private void drinkEnergy() {
        if (energyDrink >= 1) {
            playerHP += 5;
            System.out.println("   Du hast den Energydrink getrunken dich um 5HP geheilt!");
            energyDrink--;
            inventar.remove("energydrink");
        } else {
            System.out.println("   Du hast kein Energy mehr du Junkie!");
        }
        inventar();
    }
    
    private void inspectStein() {
        if (stein == 1) {
            System.out.println("   Ein Stein. Was hast du erwartet?");
        } else {
            System.out.println("   Ungültiger Befehl");
        }
        inventar();
    }
    
    private void drinkHeiltrank() {
        if (heiltrank >= 1) {
            System.out.println("   Heiltrank getrunken! Du wurdest geheilt!");
            playerHP += new java.util.Random().nextInt(10) + 5;
            heiltrank--;
            if (heiltrank == 0) {
                inventar.remove("Heiltrank");
            }
        } else {
            System.out.println("   Du hast keinen Heiltrank mehr!");
        }
        inventar();
    }
    
    private void inspectHalskette() {
        System.out.println("   Die Halskette ist wunderschön. (Sie erhöht deine Abwehrchance).");
        inventar();
    }
    
    private void invalidCommand() {
        System.out.println("   Falsche Eingabe!");
        inventar();
    }
```

### Nachher

```java

            if (inventaropen == 0) {
                openInventar();
                handleCommand();
            } else {
                closeInventar();
            }
        }
    
        private void openInventar() {
            System.out.println("\n\n   Inventar geöffnet!");
            System.out.println("   Diese Sachen befinden sich in ihrem Inventar: ");
            displayInventarItems();
            System.out.println("\n   Schreib den Namen des Objektes um es auszuwählen oder 'exit' um das Inventar zu verlassen");
            System.out.print("> ");
        }
    
        private void closeInventar() {
            inventaropen = 0;
            System.out.println("   Inventar verlassen!");
            playerInput();
        }
    
        private void displayInventarItems() {
            for (int i = 0; i < inventar.size(); i++) {
                System.out.print(inventar.get(i));
                if (i < inventar.size() - 1) {
                    System.out.print(", ");
                }
            }
        }
    
        private void handleCommand() {
            command = sc.nextLine().toLowerCase();
            switch (command) {
                case "stab":
                    equipStab();
                    break;
                case "exit":
                    exitInventar();
                    break;
                case "schwert":
                    equipSchwert();
                    break;
                case "energydrink":
                    drinkEnergy();
                    break;
                case "stein":
                    inspectStein();
                    break;
                case "heiltrank":
                    drinkHeiltrank();
                    break;
                case "halskette":
                    inspectHalskette();
                    break;
                default:
                    invalidCommand();
            }
        }
    
        private void equipStab() {
            if (stab == 1) {
                System.out.println("   Stab als Waffe ausgerüstet!");
                stabequip = 1;
                schwertequip = 0;
            } else {
                System.out.println("   Ungültiger Befehl!");
            }
            handleInventory();
        }
    
        private void exitInventar() {
            inventaropen = 1;
            handleInventory();
        }
    
        private void equipSchwert() {
            if (schwert == 1) {
                System.out.println("   Schwert als Waffe ausgerüstet");
                schwertequip = 1;
                stabequip = 0;
            } else {
                System.out.println("   Ungültiger Befehl!");
            }
            handleInventory();
        }
    
        private void drinkEnergy() {
            if (energyDrink >= 1) {
                playerHP += 5;
                System.out.println("   Du hast den Energydrink getrunken dich um 5HP geheilt!");
                energyDrink--;
                inventar.remove("energydrink");
            } else {
                System.out.println("   Du hast kein Energy mehr du Junkie!");
            }
            handleInventory();
        }
    
        private void inspectStein() {
            if (stein == 1) {
                System.out.println("   Ein Stein. Was hast du erwartet?");
            } else {
                System.out.println("   Ungültiger Befehl");
            }
            handleInventory();
        }
    
        private void drinkHeiltrank() {
            if (heiltrank >= 1) {
                System.out.println("   Heiltrank getrunken! Du wurdest geheilt!");
                playerHP += getRandomNum(10, 5);
                heiltrank--;
                if (heiltrank == 0) {
                    inventar.remove("Heiltrank");
                }
            } else {
                System.out.println("   Du hast keinen Heiltrank mehr!");
            }
            handleInventory();
        }
    
        private void inspectHalskette() {
            System.out.println("   Die Halskette ist wunderschön. (Sie erhöht deine Abwehrchance).");
            handleInventory();
        }
    
        private void invalidCommand() {
            System.out.println("   Falsche Eingabe!");
            handleInventory();
        }
    
        private void playerInput() {
            // Implement player input logic here
        }
    
        // setters und getters
    }
```

### Ablauf

Ich habe eine inventarhandler-klasse erstellt die die logik der inventar methoden kapselt.
danach hab ich die inventar methode vereinfacht und sie initalisiert nur noch die inventoryhandler Klasse
zuletzt habe ich getter und setter erstellt damit bestimmte funktionen erhalten bleiben.
