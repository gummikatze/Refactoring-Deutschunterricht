import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
* Java Text-Adventure zu Beginn der Ausbildung.
* Verwendung genehmigt durch Ausbilder und Author.
* Bearbeitet durch Kay Beeger
*
* @author  Florian Anger, Kay Beeger
*/

public class Game {

    // Alle Integer die für das Spiel notwendig sind
    int min, max, inventaropen, truhelinks, truherechts, heckeschwachpunkt, truhemitte, askharald, askbesen, skipInput,
            stab, stabequip, schwert, energyDrink, schwertequip, schreibtisch, key, mauseRing, regal, magier, stein,
            lichtinhöhle, playerburned, feeintro,
            heiltrank, halskette, rätselgelöst, feedefeated,
            stealchance, steinmesserzimmer, zimmerintro, saalintro, korridorintro, höhleintro;
    int playerAC, playerDmg, playerdmgmax, playerdmgmin, playerGold, playerHP, playerTurn, playerHitAC;
    int monsterAC, monsterHitAC, monsterdmg, monsterdmgmin, monsterdmgmax, monsterHP, monsterEncounter, monsterTurn;
    

    // Die verschiendenen Strings
    String userName, position, monsterdrop = "";
    String command = "";

    // Das Zauberbuch was die Zauber enthält
    ArrayList<String> spellbook = new ArrayList<>();
    ArrayList<String> inventar = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    Random random = new Random();

    private int getRandomNum(int limit, int base) {
        return random.nextInt(limit) + base;
    }
   
    
    // Hiermit wird das Spiel gestartet
    public static void main(String[] args) {
        new Game().playerSetup();
    }

    // Das Inventarsystem. Warscheinlich das Komplexeste am ganzen Spiel (RIP)
    // Update: Das Monsterattack() wurde schlimmer (RIP2.0)
    public void inventar() {
        InventoryHandler inventoryHandler = new InventoryHandler(inventaropen, inventar, sc, stab, schwert, energyDrink, stein, heiltrank, playerHP, stabequip, schwertequip);
        inventoryHandler.handleInventory();
        inventaropen = inventoryHandler.getInventaropen();
        playerHP = inventoryHandler.getPlayerHP();
        stabequip = inventoryHandler.getStabequip();
        schwertequip = inventoryHandler.getSchwertequip();
    }
    
    public class InventoryHandler {
        private int inventaropen;
        private List<String> inventar;
        private Scanner sc;
        private int stab, schwert, energyDrink, stein, heiltrank, playerHP;
        private int stabequip, schwertequip;
        private String command;
    
        public InventoryHandler(int inventaropen, List<String> inventar, Scanner sc, int stab, int schwert, int energyDrink, int stein, int heiltrank, int playerHP, int stabequip, int schwertequip) {
            this.inventaropen = inventaropen;
            this.inventar = inventar;
            this.sc = sc;
            this.stab = stab;
            this.schwert = schwert;
            this.energyDrink = energyDrink;
            this.stein = stein;
            this.heiltrank = heiltrank;
            this.playerHP = playerHP;
            this.stabequip = stabequip;
            this.schwertequip = schwertequip;
        }
    
        public void handleInventory() {
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
    
    
        public int getPlayerHP() {
            return playerHP;
        }
    
        public int getInventaropen() {
            return inventaropen;
        }
    
        public int getStabequip() {
            return stabequip;
        }
    
        public void setStabequip(int stabequip) {
            this.stabequip = stabequip;
        }
    
        public int getSchwertequip() {
            return schwertequip;
        }
    
        public void setSchwertequip(int schwertequip) {
            this.schwertequip = schwertequip;
        }
    }
    


    // Nun geht es ins Startmenü für den fertigen Spieler
    public void playerSetup() {
        playerGold = 0;
        playerHitAC = getRandomNum(20, 0);
        playerAC = 10;
        playerHP = 15;
        spellbook.add(" Feuerhand");
        spellbook.add(" Eisstrahl");

        startmenü();
    }

    // Sofern ein Kampf beginnt und es ein Goblin ist, übernimmt monsterHP etc die
    // Werte des Goblins bis der Kampf endet
    public void goblinSetup() {
        monsterHitAC = getRandomNum(20, 0);
        monsterAC = 5;
        monsterHP = 8;
        monsterdmg = getRandomNum(6, 2);
        monsterdrop = "energydrink" + "schwert";
    }

    // Die Stats der Fee für den Kampf. Tipp: Eisstrahl und Donner ist gut gegen sie
    public void feeSetup() {
        monsterHitAC = getRandomNum(20, 1);
        monsterAC = 5;
        monsterHP = 12;
        monsterdmg = getRandomNum(7, 3);
        monsterdrop = "feenstaub" + "feenstab";
    }

    // Der Skaven als Feind
    public void skavenSetup() {
        monsterHP = 50;
        monsterAC = 10;
        monsterHitAC = getRandomNum(20, 4);
        monsterdmg = getRandomNum(10, 1);
        monsterdrop = "Flammenwerfer, Heiltrank, Heilzauber";
    }

    // Printet aus wie viel Gold der Spieler besitzt.
    public void gold() {
        System.out.print("    Du hast: " + playerGold + " Kronen.");
        playerInput();
    }

    // Das ist das Startmenü in dem man das Spiel beginnt
    public void startmenü() {
        position = "startmenü";
        for (int i = 0; i < 2; i++) {
            System.out.println("");
        }

        System.out.println(
                "   Willkommen! Tippe 'hilfe' um Hilfe zum Spiel zu erhalten und 'start' um das Spiel zu starten");
        playerInput();
    }

    // Falls man Hilfe eintippt wird es erkannt und hierhin weitergeleitet. Die
    // Erkennung ist weiter unten
    public void hilfe() {
        System.out.print(
                "   Du kannst mit Objekten interagieren (zb. 'buch') \n   Du kannst mit Personen sprechen (zb. 'gandalf') \n   Du kannst auch Wege wählen (zb. 'pforte') \n   Du kannst mithilfe von 'zauberbuch' deine Zauber ansehen. Du kannst sie auch außerhalb von Kämpfen benutzen aber pass auf! \n   Wenn du 'kronen' eingibst, schaust du nach wie viel Kronen du besitzt. \n   Du kannst mit 'Inventar' dein Inventar öffnen und verschiedene Waffen ausrüsten, sowie Items nutzen.");
        playerInput();
    }

    // Bei Start wird man auf das Zimmer gebracht
    public void gameStart() {
        position = "zimmer";
        System.out.println("   Dein Abenteuer beginnt!");
        zimmer();
    }

    // Wenn der Spieler etwas eingeben muss wird er hierhin weitergeleitet und dann
    // auf den ChoiceHandler der die Antwort vergleicht
    public void playerInput() {
        for (int i = 0; i < 2; i++) {
            System.out.println("");
        }
        System.out.print("> ");
        command = sc.nextLine().toLowerCase();
        choiceHandler();
    }

    // Das Zimmer. von dort aus darf der Spieler Befehle eingeben um zu interagieren
    public void zimmer() {
        position = "zimmer";
        if (zimmerintro == 0) {
            zimmerintro = 1;
            System.out.print(
                    "   Du erwachst mit den sanften Sonnentrahlen die durch die kunstvollen 'Fenster' scheinen. \n   Der Raum ist geschmückt mit 'Wandteppichen' die große Zauberer darstellen, eine Menge an 'Regalen' vollgestopft mit Büchern und einer schimmernden 'Glaskugel', die auf deinem Tisch schwebt. \n   Du reibst dir müde die Augen und erkennst deinen Stab, angelehnt an deinem 'Schreibtisch'. \n   Du bist eine Novizenmagierin und lebst in einer Akademie für Magie. Jahrelang hast du hier studiert, mitten in einem magischen Wald abgelegen von jeglich anderer Zivilsation. \n   Heute ist deine Abschlussprüfung. Du hast dich endlos lange mit deinen Büchern befasst um dich darauf vorzubereiten und endlich offiziell den Titel der Magierin zu erhalten. \n   Du brauchst nur deinen 'Stab' und musst dann zur Tür hinaus, aber vielleicht schaust du dich noch erst im Raum um? \n   Mögliche Wege: Tor");
        } else {
            System.out.print("   Du befindest dich in deinem Zimmer. \n   Mögliche Wege: Tor");
        }
        playerInput();
    }

    public void stab() {
        if (stab == 0) {
            stab = 1;
            stabequip = 1;
            System.out.print("   Du erhältst deinen Stab!");
            inventar.add("stab");
        } else {
            System.out.print("   Du hast schon deinen Stab!");
        }
        playerInput();
    }

    public void schreibtisch() {
        // int Schreibtisch sorgt dafür dass man nicht endlos mit dem Scheibtisch
        // interagieren darf sondern nur 1 mal
        if (schreibtisch == 0) {
            schreibtisch = 1;
            System.out.print("   Du durchsuchst deinen Schreibtisch und erhältst 20 Kronen!");
            playerGold = playerGold + 20;
        } else {
            System.out.print("   Du warst schon an deinem Schreibtisch!");
        }
        playerInput();
    }

    // Der nächste Raum nach dem Zimmer. Von hier aus darf man wieder mit
    // verschiedenen Objekten interagieren
    public void korridor() {
        if (stab == 0) {
            System.out.print("   Du brauchst erst deinen Stab!");
        } else {
            position = "korridor";
            if (korridorintro == 0) {
                korridorintro++;
                System.out.print(

                        "   Vor dir erstrecken sich die endlosen Korridore der Akademie. Die glühenden, magischen 'Kronleuchter' erhellen den Weg und erstecken sich in die Ferne. \n   Trotzdem fühlen sie sich nach all der Zeit vertraut an... \n   In beide Richtungen sind überall 'Magier' zu sehen, die hastig auf dem Weg zur Abschlussprüfung sind. Alles sind bekannte Gesichter aus deinem Lehrjahr. \n   Doch in der Ferne erblickst du deinen guten Freund 'Harald'. Er starrt Seelenlos in die Ferne, in die Richtung 'Garten' der Akademie. \n   Die Wände sind mit einer vielzahl an 'Dekorationen' versehen, und du erspähst einen magischen 'Besen' der im Getummel die Böden kehrt. \n   Mögliche Wege: Pforte, Tor");
            } else {
                System.out.println("   Du bist in den Korridoren der Akademie. \n   Mögliche Wege: Tor, Pforte");
            }
        }
        playerInput();
    }

    // Wenn man das Steinmesser im Zimmer benutzt zerstört man damit das Fenster und
    // es werden andere Interaktionen freigeschalten
    public void fenster() {
        if (steinmesserzimmer == 0) {
            System.out.print(
                    "   Du öffnest das Fenster und schaust heraus. Eine kalte Brise trifft auf deine Haut. Die Sonne scheint stark und es befinden sich nur ein paar Wolken in der Luft.");
        } else {
            System.out.print(
                    "Du starrst dein kaputtes Fenster an. Wie teilst du das der Leitung mit? Du bist ein Clown.");
        }
        playerInput();
    }

    public void wandteppich() {
        System.out.print(
                "   Du betrachtest die Wandteppiche genauer. In deinem Zimmer befinden sich eine Darstellung von Groyld, wie er die Füße eines Ogers einfriert bevor dieser auf ein kleines Dorf zurennen kann. \n   Du erblickst auch Hald, und wie sie tapfer mit einem Windstoß Pfeile zurückwirft, die auf Bürger zugeflogen sind. Du verspürst den Wunsch auch so etwas heldenhaftes zu tun.");
        playerInput();
    }

    public void glaskugel() {
        System.out.print(
                "   Deine Glaskugel. Sie strahlt eine kleine, beruhigende Flamme aus. Einer deiner Freunde zeigte dir einst, wie man sie magisch in diese Glaskugel einschließt und sie brennen lässt.");
        playerInput();
    }

    public void regal() {
        if (regal == 0) {
            System.out.print(
                    "   Hier sind all die alten, duftenden Bücher verstaut die du zum studieren benutzt hast. Du würdest eher sterben als nochmal hineinzusehen. \n   Plötzlich fällt dir eine Schriftrolle auf, die zwischen all den Büchern hervorragt. Es ist die Zauberschriftrolle die du mal gekauft hast! Hastig schreibst du den Zauber in dein Zauberbuch. \n   Du hast den Zauber 'Steinmesser' erlernt!!");
            spellbook.add(" Steinmesser");
        } else {
            System.out.print("Du warst schon am Regal!");
        }
        playerInput();
    }

    public void dekoration() {
        System.out.print(
                "   Du betrachtest die Dekorationen, die an der Wand hängen. Dort befinden sich vorallem Bilder von großen Persönlichkeiten oder geschnitzte Runen, aber in der Ferne sind auch Jagdtrophäen zu sehen.");
        playerInput();
    }

    public void besen() {
        if (askbesen == 0) {
            position = "besen";
            System.out.print(
                    "   Du betrachtest den Besen, und wie er den Staub kehrt. Oftmals wird er von den Novizen einfach weggestoßen wenn er im Weg ist. \n   Als du den Staub betrachtest den er zusammengekehrt hat, siehst du ein funkeln im darin. Du näherst dich und erkennst einen Ring darin. Möchtest du den Ring 'nehmen' oder ihn lieber dort 'lassen'?");
            playerInput();
        } else {
            System.out.print("Du hast schon mit dem Besen interagiert!");
            playerInput();
        }
    }

    public void harald() {
        position = "harald";
        System.out.print(
                "   Du näherst dich Harald. Er bemerkt dich erst nicht. \n   \"Oh, Hallo. Geh schon mal zum Prüfungssaal vor, ich komme nach.\" Er scheint sehr bedrückt zu sein. Du könntest 'fragen' wie er sich fühlt oder 'gehen'.");
        playerInput();
    }

    public void kronleuchter() {
        System.out.print("   Die Kronleuchter leuchten vor sich hin.");
        playerInput();
    }

    // Hier könnte man etwas stehlen, doch man hat eine Chance ein Game Over zu
    // bekommen und wenn man sich dagegen entscheidet bekommt man eine größere
    // Belohnung.
    public void magierstehlen() {
        position = "magierstehlen";
        if (magier == 0) {
            magier = 1;
            System.out.print(
                    "   In dem ganzen Getummel würde wohl keiner merken, wenn ihm etwas gestohlen wird. Möchtest du es wagen, etwas zu stehlen? \n   (j/n)");
            playerInput();

        } else {
            System.out.print("   Das kannst du nicht noch einmal tun!");
            playerInput();
        }
    }

    // Der Goblin wird initialisiert, monsterwerte werden für ihn übernommen, die
    // Position wird bestimmt für die Zauber und der Spieler darf zuerst angreifen
    public void goblinFight() {
        position = "goblinfight";
        goblinSetup();
        playerAttack();
    }

    public void skavenFight() {
        position = "skavenfight";
        skavenSetup();
        playerAttack();
    }

    // Der Spieler greift an. Jeder Zauber initiiert monsterAttack, wo erst dort
    // geprüft wird ob das Monster noch lebt und ob der Spieler den zukünftigen
    // Schaden überlebt
    public void playerAttack() {
        for (int i = 0; i < 6; i++) {
            System.out.println("");
        }
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("            Deine HP: " + playerHP + "       HP des Gegners: " + monsterHP);
        System.out.println("----------------------------------------------------------------------------");
        if (stabequip == 1) {
            System.out.println("   Du darfst mit einem deiner Zauber angreifen!");
            Zauberbuch();
            switch (command) {
                case "feuerhand":
                    feuerhand();
                case "eisstrahl":
                    eisstrahl();
                case "steinmesser":
                    steinmesser();
                case "donner":
                    donner();
                case "feuerball":
                    feuerball();
                default:
                    System.out.println("   Falscher Befehl!");
                    playerAttack();
            }
            // Wenn das Schwert ausgerüstet ist geht der Kampf automatisch da man keine
            // Zauber nutzen darf
        } else if (schwertequip == 1) {
            System.out.println("Drücke Enter um mit deinem Schwert anzugreifen!");
            schwertAngriff();
        }
    }

    public void schwertAngriff() {
        playerInput();
        System.out.println("   Du benutzt dein Schwert!");
        playerHitAC = getRandomNum(20, 5);
        if (playerHitAC > monsterAC) {
            playerDmg = getRandomNum(6, 4);
            monsterHP = monsterHP - playerDmg;
            System.out.println("   Du hast dem Gegner " + playerDmg + " Schaden zugefügt!");
        } else {
            System.out.println("   Du hast verfehlt!");
        }
        ;
        monsterAttack();

    }
  
   


    // Monster greift an, lies das Kommentar davor
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
                }
                break;
            case ("feefight"):
                if (monsterHP > 0) {
                    System.out.println("   Die Fee ist dran!");
                    monsterHitAC = getRandomNum(20, 1);
                    if (monsterHitAC > playerAC) {
                        if (monsterHitAC > 15) {
                            monsterdmg = getRandomNum(4, 2);
                            playerHP = playerHP - monsterdmg;
                            System.out.println("   Die Fee fügt dir " + monsterdmg + " Schaden zu!");
                        } else {
                            monsterdmg = playerHP /2;
                            playerHP = playerHP - monsterdmg;
                            System.out.println(
                                    "   Die Fee benutzt ihren Zauber 'AUSWEIDUNG DER STERBLICHEN' und fügt dir "
                                            + monsterdmg + " Schaden zu!");
                        }
                    } else {
                        System.out.println("   Die Fee hat bei ihrem Angriff verfehlt!");
                    }
                    ;
                    monsterHitAC = getRandomNum(20, 1);
                    if (monsterHitAC > playerAC) {
                        monsterdmg = getRandomNum(1, 1);
                        playerHP = playerHP - monsterdmg;
                        System.out.println(
                                "   Die Fee greift zum zweiten mal an und fügt dir " + monsterdmg + " Schaden zu!");
                    } else {
                        System.out.println("   Die Fee hat bei ihrem zweiten Angriff verfehlt!");
                    }
                    ;
                } else {
                    position = "";
                    System.out.println(
                            "   Du hast die Fee besiegt! \n   Die Fee hat einen Heiltrank und einen neuen Zauber: Leuchtorb gedroppt!");
                    if (heiltrank > 0) {
                        feedefeated = 1;
                        heiltrank++;
                        spellbook.add("Leuchtorb");
                    } else {
                        feedefeated = 1;
                        inventar.add("Heiltrank");
                        spellbook.add("Leuchtorb");
                        heiltrank++;
                    }
                    labyrinth34();

                }
                break;
            case "skavenfight":
                if (playerburned == 1) {
                    playerHP = playerHP - 1;
                    monsterHP = monsterHP - 1;
                    System.out.println("   Beide nehmen durch die Flammen 1 Schaden!");
                }
                if (monsterHP > 0) {
                    System.out.println("   Der Skaven ist dran!");
                   

                    monsterHitAC = getRandomNum(20, 3);
                    if (monsterHitAC > playerAC) {
                        if (monsterHitAC > 15) {
                            monsterdmg = getRandomNum(10, 1);
                            playerHP = playerHP - monsterdmg;
                            System.out.println("   Der Skaven fügt dir " + monsterdmg + " Schaden zu!");
                            monsterdmg = getRandomNum(10, 1);
                            monsterHP = monsterHP - monsterdmg;
                            System.out.println("   Der Skaven fügt sich selber " + monsterdmg + " Schaden zu!");
                        } else {
                            monsterdmg = getRandomNum(10, 1);
                            playerHP = playerHP - monsterdmg;
                            System.out.println(
                                    "   Der Skaven benutzt seinen Flammenwerfer und fügt dir "
                                            + monsterdmg
                                            + " Schaden zu! Du bist verbrannt! Der Skave zündet sich auch selbst mit an!");
                            playerburned = 1;
                            monsterdmg = getRandomNum(10, 1);
                            monsterHP = monsterHP - monsterdmg;
                            System.out.println("   Der Skaven fügt sich selbst " + monsterdmg + " Schaden zu!");
                        }
                    } else {
                        System.out.println("   Der Skaven hat bei seinem Angriff verfehlt!");
                    }
                    ;
                    monsterHitAC = getRandomNum(20, 0);
                    if (monsterHitAC > playerAC) {
                        monsterdmg = getRandomNum(1, 1);
                        playerHP = playerHP - monsterdmg;
                        System.out.println(
                                "   Der Skaven greift zum zweiten mal an und fügt dir " + monsterdmg + " Schaden zu!");
                    } else {
                        System.out.println("   Der Skaven hat bei seinem zweiten Angriff verfehlt!");
                    }
                    ;
                } else {
                    position = "";
                    System.out.println(
                            "   Du hast den Skaven besiegt! \n   Er droppt einen Heilzauber, seinen Flammenwerfer und einen Heiltrank für dich!");
                    if (heiltrank > 0) {
                        heiltrank++;
                        spellbook.add("Heilung");
                    } else {
                        inventar.add("Heiltrank");
                        spellbook.add("Heilung");
                        heiltrank++;
                    }
                    ending1();

                }
                break;
        }
        if (playerHP > 0) {
            playerAttack();
        } else if (monsterHP <= 0) {
            position = "";
            System.out.println(
                    "   Du hast den Skaven besiegt! \n   Er droppt einen Heilzauber, seinen Flammenwerfer und einen Heiltrank für dich!");
            if (heiltrank > 0) {
                heiltrank++;
                spellbook.add("Heilung");
            } else {
                inventar.add("Heiltrank");
                spellbook.add("Heilung");
                heiltrank++;
            }
            labyrinth41();
        } else {
            System.out.print("  DU BIST GESTORBEN!!");
            startmenü();
        }
    }

    // Hier gibt der Spieler seinen Namen ein
    public void saal() {
        position = "saal";
        if (saalintro == 0) {
            saalintro = 1;
            System.out.print(
                    "   Du trittst durch die Eichenholztüren und betrittst die Prüfungshalle der Akademie. \n   In der Mitte der Halle befindet sich ein erhöhter Podest, auf dem die Prüfer und 'Lehrmeister' der Akademie bereits auf alle warten. \n   Du fühlst dein Herz pochen. Der Zeitpunkt ist gekommen, und du beginnst deinen Platz einzunehmen. Bald wird entschieden ob du den Titels des Magiers würdig bist. \n   Auf deinem Tisch befindet sich nichts weiter als ein Fragebogen. \n   Selbst mit all dem Druck schaffst du es fast alle Fragen zu beantworten. Dann ertönt plötzlich ein lauter Klang: Die Zeit ist um. Du musst aber noch deinen Namen aufschreiben! \n   Schreibe deinen Namen auf den Fragebogen: ");
            userName = sc.nextLine();
            System.out.println("");
            System.out.print("   Du hast ein gutes Gefühl als du deinen Namen " + userName
                    + " auf den Bogen schreibst. \n   Doch nun war es Zeit für den zweiten Teil der Prüfung gekommen: Die Praxis. Um deine Anweisung zu erhalten musst du jedoch erst nach draußen in Richtung des Gartens. \n   Du siehst wie etliche deiner Mitschüler schon aufstehen und sich in Richtung des Gartens bewegen. \n   Mögliche Wege: Hinaus, Pforte");
        } else {
            System.out.print("   Du befindest dich im Saal. \n   Mögliche Wege: Hinaus, Pforte");

        }
        playerInput();
    }

    // Ich geh mit meiner Lateeeeeerne, und meine Laterne mit mir
    public void laternen() {
        System.out.print("  Du betrachtest die Laternen, die im Raum herumschweben");
        playerInput();
    }

    public void schriftrollen() {
        System.out.print("   Die Schriftrollen und Artefakte haben sich hinter Glas versteckt.");
        playerInput();
    }

    public void lehrmeister() {
        System.out.print(
                "   Dein Lehrmeister sieht heute sehr angespannt aus. Trotzdem wandert er mit aufmerksamen Blick durch den Saal und betrachtet euch beim herausgehgen.");
        playerInput();
    }

    // Hier muss der Spieler das Labyrinth betreten
    public void garten() {
        position = "garten";
        System.out.print(
                "   Als du den Garten erreichst, erreicht deine Nase ein betörender Duft von exotischsten Blumen und Bäume die glitzernde, schimmernde Früchte in allen Farben tragen \n   Vor dir ersteckt sich auch ein System aus Hecken die ein gigantisches Labyrinth bilden. \n   Dein Lehrmeister erklärt dass jeder einzeln das andere Ende des Labyrinths erreichen muss. \n   Dein Name wird aufgerufen: \" "
                        + userName + ", du wirst als erstes durchgehen.\" \n\n   Drücke Enter um fortzufahren.");
        playerInput();
        labyrinth10();
    }

    // Anfang des Labyrinthes. 10 steht für 1.0, wenn zb links und rechts gelaufen
    // werden können wird es 2.1 und 2.2 etc... durchnummeriert
    public void labyrinth10() {
        position = "labyrinth10";
        System.out.println(
                "   Du stehst vor dem Eingang des Labyrinths. Vor dir ersteckt sich ein Weg nach 'links' und 'rechts'.");
        playerInput();
    }

    public void labyrinth21() {
        position = "labyrinth21";
        System.out.print(
                "   Du entscheidest dich für den linken Weg. \n   Du gehst den Weg, als plötzlich die Hecken hinter dir zusammenwachsen und den Weg zurück versperren: Du hast keine andere Wahl als weiter zu laufen. \n   Als du weiterläufst und der Weg nach links abbiegt, erscheint plötzlich ein Goblin vor dir! Du hast keine andere Wahl als dich durchzukämpfen.! \n\n   Drücke Enter um fortzufahren.");
        playerInput();
        goblinFight();
        labyrinth23();
    }

    public void labyrinth22() {
        position = "labyrinth22";
        System.out.print(
                "   Du entscheidest dich für den rechten Weg. \n   Du gehst den Weg, als plötzlich die Hecken hinter dir zusammenwachsen und den Weg zurück versperren: Du hast keine andere Wahl als weiter zu laufen. \n   Als du weiterläufst und der Weg nach links abbiegt, erscheint plötzlich ein Goblin vor dir! Du hast keine andere Wahl als dich durchzukämpfen.! \n\n   Drücke Enter um fortzufahren.");
        playerInput();
        goblinFight();
        labyrinth23();
    }

    // Goblin besiegt
    public void labyrinth23() {
        position = "labyrinth23";
        System.out.print(
                "   Der böse Goblin liegt besiegt am Boden und du stehst nun an einer Kreuzung. \n   Du kannst den Weg nach 'links' order 'rechts' nehmen.");
        playerInput();
    }

    // Raum mit der Tür
    public void labyrinth24() {
        position = "labyrinth24";
        System.out.print(
                "   Du gehst nach links und kommst zu einer mysteriösen Tür. \n   Die Tür ist verschlossen. Du kannst 'gehen' oder die Tür 'untersuchen'.");
        playerInput();
    }

    public void Tür() {
        if (key == 0) {
            System.out.print("   Du scheinst einen Schlüssel zu brauchen.");
            playerInput();
        } else if (key == 1) {
            System.out.print(
                    "   Mit dem rostigen, alten Schlüssel schaffst du es die Tür aufzuschließen. Du entscheidest dich durch sie hindurch zu treten. \n   In diesem Raum gibt es drei Truhen. Du kannst die 'erste', 'zweite' oder 'dritte' Truhe öffnen.");
            Truhen();
        } else {
            System.out.print(
                    "   Du entscheidest dich nicht gierig zu sein und dich mit der einen Truhe zufrieden zugeben.");
            playerInput();
        }
    }

    // Truhen...
    public void Truhen() {
        position = "Truhen";
        key = key + 1;
        System.out.print("\n   Die Truhen erwarten erwartungsvoll welche du wählst.");
        playerInput();
    }

    // Hier ist der Weg mit drei Pfaden
    public void labyrinth25() {
        position = "labyrinth25";
        System.out.print(
                "   Du gehst den rechten Weg und triffst auf drei verschiedene Pfade. \n   Du kannst 'rechts', 'links' oder die 'mitte' nehmen. \n   Du kannst natürlich auch wieder zurück mit 'gehen'");
        playerInput();
    }

    // Raum mit dem Schlüssel
    public void labyrinth31() {
        if (key > 0) {
            System.out.print("   Hier ist nichts mehr.");
        } else {
            position = "labyrinth31";
            System.out.print(
                    "   Du gehst nach links und stößt auf einen alten Schlüssel. Du hebst ihn auf. \n   'gehen'");
            key = key + 1;
        }
        playerInput();
    }

    // Raum mit BARRY
    public void labyrinth32() {
        position = "labyrinth32";
        System.out.print(
                "   Du gehst den mittleren Weg und entdeckst eine freundliche Figur. \n   \"HALLO!!!!! ICH BIN BARRY. BARRY VERKAUFT DIR TOLLE SACHEN.\" \n   \"BARRY GANZ TOLLER VERKÄUFER!!!\" \n   Barry bietet dir verschiedene Gegenstände an. \n   Du kannst 'kaufen' oder 'gehen'.");
        playerInput();
    }

    // Shopkeeper Barry
    public void shopkeeper() {
        position = "shopkeeper";
        System.out.println("------------------------------------------------------------");
        System.out.println("               Dein Gold: " + playerGold + ".");
        System.out.println("------------------------------------------------------------");
        System.out.println(
                "   BARRY bietet dir einen paar Objekte an: \n     Stein: 2 Gold \n     Heiltrank: 20 Gold \n     Halskette: 30 Gold");
        System.out.print("   Du kannst die Sachen kaufen indem du ihren Namen eingibst oder 'gehen'");
        playerInput();
    }

    // Schwachstelle in der Hecke
    public void labyrinth33() {
        position = "labyrinth33";
        if (heckeschwachpunkt == 0) {
            System.out.print(
                    "   Du gehst in die Mitte und entdeckst eine Schwachstelle in den Hecken. \n   Vielleicht kannst du sie mit einem Zauber öffnen?");
        } else {
            System.out.print(
                    "   In der Hecke ist ein Loch der zu einem Pfad führt. Möchest du 'weiter' oder 'gehen'?");
        }
        playerInput();
    }

    // Fee Encounter
    public void labyrinth34() {
        position = "labyrinth34";
        if (feedefeated == 0) {
            System.out.println(
                    "   Du versuchst durch den Pfad zu gehen, doch plötzlich erscheint eine Fee und versperrt dir den Weg. \n   \" WARUM VERBRENNST DU MEINE HECKEN? ICH ZEIGE DICH AN!\" \n   Die Fee rast auf dich zu.");
            System.out.print("\n   Drücke Enter um zu kämpfen.");
            position = "feefight";
            playerInput();
            feeSetup();
            playerAttack();
        } else {
            System.out.println("   Du gehst weiter un erreichst die andere Seite da du die Fee besiegt hast. dine mu");
            labyrinth41();
        }
    }

    // Raum mit schäbischer Statue
    public void labyrinth41() {
        position = "labyrinth41";
        if (feeintro == 0) {
            feeintro = 1;

            System.out.println(
                    "   Du schaust dich im Raum um. Du erkennst eine massive 'Statue' in der Mitte, eine 'Steintafel' zu deiner rechten. \n   Du erkennst auch einen 'Pfad' vorwärts, hinter der massiven Statue. \n   Mögliche Wege: Pfad, Loch");
            playerInput();
        } else {
            System.out.println("   Du bist im Raum mit der Statue und einer Steintafel \n Mögliche Wege: Pfad, Loch.");
            playerInput();
        }
    }

    // Die heilige Statue (Ending 1)
    public void statue() {
        System.out.println("   Du näherst dich der Statue. Sie fängt an zu sprechen");
        System.out.println(
                "   \"Äggerle ond a Kuah, deggad älle Armud zua.\"\n   Du scheinst nicht so ganz zu verstehen was um alles in der Welt die Statue sagt. \n   \"Hallöle Mädle! Komm A baar Medr zu mir! Ich bruch dei Helfa du daube Nuß!\" \n   Du entscheidest dich näher an die Statue zu gehen.\n   \"I hann A Brez em Gschid ond hob mei Gloid verlora.\" \n   Die Statue scheint etwas von dir zu verlangen. Aber was?\n   \"Du frechs Luadr I bruch dei Helfa! Geh A Schidgg duschur gradaus, noch rechts, ond nedde links da isch ebbes!\"\n");
        playerInput();
    }

    // Rätsel(Lösung: Karte)
    public void steintafel() {
        if (rätselgelöst == 0) {
            position = "steintafel";
            System.out.println(
                    "   Was hat Städtele, aber koini Hais? \n   Wälderle, aber koini Baimle? \n   Wasserle, aber koini Fischle? \n\n   Ein Rätsel. Vielleicht kannst du die Antwort herausfinden? Wenn nicht, kannst du jederzeit 'gehen'.");
            playerInput();
        } else {
            System.out.println("   Da warst du schon!");
            labyrinth41();
        }

    }

    public void maus() {

    }

    // Dort kriegt man den Feuerball
    public void secretSteintafel() {
        System.out.println(
                "   Du hast das Rätsel gelöst! Die Steintafel fällt auf den Boden, und hinter ihr erkennst du eine Schriftrolle mit einem neuem Zauber: Feuerball!\n");
        spellbook.add("Feuerball");
        rätselgelöst = 1;
        labyrinth41();
    }

    // Höhle
    public void labyrinth42() {
        position = "labyrinth42";
        if (höhleintro == 0) {
            höhleintro = 1;
            System.out.print(
                    "   Du entscheidest dich den Pfad entlang zu gehen. Der Weg führt dich unter den Boden. \n   Es ist dunkel, und du erkennst kaum noch etwas. \n\n   Mögliche Wege: Hinaus, Weiter");
            playerInput();
        } else {
            System.out.print("   Du bist in der Höhle. \n\n Mögliche Wege: Hinaus, Weiter");
        }
        playerInput();
    }

    public void talkToSkaven() {
        position = "skaventalk";
        System.out.print(
                "   Du hast mehrere Möglichkeiten: \n    - Angreifen \n    - Leugnen \n    - Nichts sagen \n    - Wegrennen \n    - Antworten");
        playerInput();
    }

    // Mein geliebter choiceHandler. Er überprüft alle Inputs der Spieler und leitet
    // sie weiter. Ich liebe ihn.
    public void choiceHandler() {
        switch (position) {
            case "startmenü":
                switch (command) {
                    case "hilfe":
                        hilfe();
                    case "start":
                        gameStart();
                    case "zauberbuch":
                        Zauberbuch();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();
                }
            case "zimmer":
                switch (command) {
                    case "stab":
                        stab();
                    case "tor":
                        korridor();
                    case "schreibtisch":
                        schreibtisch();
                    case "fenster":
                        fenster();
                    case "glaskugel":
                        glaskugel();
                    case "regalen":
                    case "regale":
                    case "regal":
                        regal();
                    case "wandteppichen":
                    case "wandteppiche":
                        wandteppich();
                    case "zauberbuch":
                        Zauberbuch();
                    case "feuerhand":
                        feuerhand();
                    case "eisstrahl":
                        eisstrahl();
                    case "steinmesser":
                        steinmesser();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    case "adlerauge":
                        adlerauge();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "korridor":
                switch (command) {
                    case "tor":
                        zimmer();
                        break;
                    case "pforte":
                        saal();
                        break;
                    case "harald":
                        harald();
                    case "garten":
                        System.out.print(
                                "   Da hast dus Kiara: Meow Meow Meow Meow Meow Meow Meow Meow Meow Meow Meow Meow Meow Meow");
                        playerInput();
                        break;
                    case "magier":
                        magierstehlen();
                        break;
                    case "kronleuchter":
                        kronleuchter();
                        break;
                    case "dekorationen":
                    case "dekoration":
                        dekoration();
                        break;
                    case "besen":
                        besen();
                        break;
                    case "zauberbuch":
                        Zauberbuch();
                        break;
                    case "feuerhand":
                        feuerhand();
                        break;
                    case "eisstrahl":
                        eisstrahl();
                        break;
                    case "steinmesser":
                        steinmesser();
                        break;
                    case "kronen":
                        gold();
                        break;
                    case "inventar":
                        inventar();
                        break;
                    case "hilfe":
                        hilfe();
                        break;
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "saal":
                switch (command) {
                    case "pforte":
                        korridor();
                    case "hinaus":
                        garten();
                    case "lehrmeister":
                        lehrmeister();
                    case "laternen":
                    case "laterne":
                        laternen();
                    case "schriftrollen":
                    case "schriftrolle ":
                        schriftrollen();
                    case "zauberbuch":
                        Zauberbuch();
                    case "feuerhand":
                        feuerhand();
                    case "eisstrahl":
                        eisstrahl();
                    case "steinmesser":
                        steinmesser();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }

            case "besen":
                switch (command) {
                    case "nehmen":
                        System.out.println(
                                "   Du nimmst den Ring auf. Er ist golden und auf ihm sind verschiedenste Runen eingraviert. Er ist außerdem mit einem Mausekopf verziert und sieht sehr kostbar aus.\n   Du verstaust ihn sicher in einer deiner Taschen. Vielleicht wird er bald als vermisst gemeldet, doch jetzt musst du dich auf die Prüfung fokussieren.");
                        mauseRing = 1;
                        korridor();
                    case "lassen":
                        System.out.println(
                                "   Du entscheidest dich es nicht aufzuheben, denn der ganze Staub und Dreck ist ekelhaft und du hast dich auf eine Prüfung zu konzentrieren.");
                        korridor();
                    default:
                        System.out.print("   Falscher Befehl!");
                        besen();

                }
            case "harald":
                switch (command) {
                    case "fragen":
                        System.out.print(
                                "   \"Ich weiß nicht ob ich die Prüfung bestehe, wir hatten so viel zu lernen.\" Er seufzt. \n   \"Aber bevor ich es vergesse: Ich Schulde dir ja noch ein paar Kronen! Hier!\" \n   Du erhältst 10 Kronen! \n   Du stellst fest dass er gerade lieber allein sein möchte und entscheidest dich, ihn erst alleine zu lassen.");
                        playerGold = playerGold + 10;
                        korridor();
                    case "gehen":
                        System.out.print("   Du entfernst dich von Harald, es wäre besser ihn in Ruhe zu lassen.");
                        korridor();
                    default:
                        System.out.print("Falscher Befehl!");
                        harald();
                }
            case "magierstehlen":
                switch (command) {
                    case "j":
                        System.out.println("   Du versuchst etwas zu stehlen...");
                        stealchance = getRandomNum(20, 0);
                        if (stealchance > 10) {
                            System.out.println("   Du hast erfolgreich Gold gestohlen!");
                            playerGold = playerGold + 5;
                            korridor();
                        } else {
                            System.out.println(
                                    "   Versuch fehlgeschlagen! Du wurdest erwischt und fliegst aus der Akademie raus! GAME OVER.");
                        }
                    case "n":
                        System.out.println(
                                "   Du bist eine gut erzogene Magierin und entscheidest dich, nichts zu stehlen. Für dein gutes Verhalten fliegen dir auf magische Art und Weise 10 Kronen in die Tasche!");
                        playerGold = playerGold + 10;
                        korridor();
                    default:
                        System.out.print("Falscher Befehl!");
                        magierstehlen();

                }

            case "labyrinth10":
                switch (command) {
                    case "links":
                        labyrinth21();
                    case "rechts":
                        labyrinth22();
                    case "zauberbuch":
                        Zauberbuch();
                    case "feuerhand":
                        feuerhand();
                    case "eisstrahl":
                        eisstrahl();
                    case "steinmesser":
                        steinmesser();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "labyrinth23":
                switch (command) {
                    case "links":
                        labyrinth24();
                    case "rechts":
                        labyrinth25();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "labyrinth24":
                switch (command) {
                    case "gehen":
                        labyrinth23();
                    case "untersuchen":
                        Tür();
                    case "zauberbuch":
                        Zauberbuch();
                    case "feuerhand":
                        feuerhand();
                    case "eisstrahl":
                        eisstrahl();
                    case "steinmesser":
                        steinmesser();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "Truhen":
                switch (command) {
                    case "erste":
                        truhelinks = 1;
                        System.out.println("   Du öffnest die linke Truhe und erhältst 10 Lebenspunkte zurück!");
                        playerHP = playerHP + 10;
                        labyrinth24();
                    case "zweite":
                        truherechts = 1;
                        System.out.println(
                                "   Du öffnest die Truhe in der mitte und fühlst eine magische Kraft in dir. Du fühlst dich mächtiger. Du lernst einen neuen Zauber: Donner.");
                        spellbook.add("Donner");
                        labyrinth24();
                    case "dritte":
                        truhemitte = 1;
                        System.out.println(
                                "   Du öffnest die rechte Truhe und spürst eine magische Kraft in dir. Du fühlst dich stärker.");
                        playerAC = playerAC + 5;
                        labyrinth24();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "labyrinth25":
                switch (command) {
                    case "links":
                        labyrinth31();
                    case "rechts":
                        labyrinth33();
                    case "mitte":
                        labyrinth32();
                    case "gehen":
                        labyrinth23();
                    case "zauberbuch":
                        Zauberbuch();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "labyrinth31":
                switch (command) {
                    case "gehen":
                        labyrinth25();
                    case "zauberbuch":
                        Zauberbuch();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "labyrinth32":
                switch (command) {
                    case "kaufen":
                        shopkeeper();
                    case "gehen":
                        labyrinth25();
                    case "zauberbuch":
                        Zauberbuch();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "shopkeeper":
                switch (command) {
                    case "stein":
                        if (playerGold >= 2) {
                            System.out.println(
                                    "   Du kaufst einen Stein von Barry. Er macht nichts. Du hast Geld verschwendet...");
                            playerGold = playerGold - 2;
                            inventar.add("stein");
                            shopkeeper();
                        } else {
                            System.out.println("   Nicht genügend Gold! Du bist ein Geringverdiener.");
                            playerInput();
                        }
                    case "heiltrank":
                        if (playerGold >= 20) {
                            System.out.println(
                                    "   Du kaufst einen Heiltrank von Barry. Du kannst ihn im Inventar trinken!");
                            playerGold = playerGold - 20;
                            if (heiltrank > 0) {
                                heiltrank++;
                            } else {
                                inventar.add("heiltrank");
                                heiltrank++;
                            }
                            shopkeeper();
                        } else {
                            System.out.print("   Nicht genügend Gold! Du bist ein Geringverdiener.");
                            playerInput();
                        }
                    case "halskette":
                        if (playerGold >= 30) {
                            System.out.println(
                                    "   Du kaufst eine Halskette von Barry. Du rüstest sie aus!");
                            playerAC = playerAC + 3;
                            playerGold = playerGold - 30;
                            inventar.add("halskette");
                            shopkeeper();
                        } else {
                            System.out.println("   Nicht genügend Gold! Du bist ein Geringverdiener.");
                            playerInput();
                        }
                    case "gehen":
                        System.out.println("   \"BARRY WARTET AUF DICH!\"");
                        labyrinth25();
                    case "barry":
                        System.out.println("   \"BARRY GANZ TOLLER VERKÄUFER, SEHR REICH!!!\"");
                        shopkeeper();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();
                }
            case "labyrinth33":
                switch (command) {
                    case "gehen":
                        labyrinth25();
                    case "weiter":
                        labyrinth34();
                    case "zauberbuch":
                        Zauberbuch();
                    case "feuerhand":
                        feuerhand();
                    case "eisstrahl":
                        eisstrahl();
                    case "steinmesser":
                        steinmesser();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();
                }
            case "labyrinth34":
                switch (command) {
                    default:
                        position = "feefight";
                        feeSetup();
                        playerAttack();
                }
            case "labyrinth41":
                switch (command) {
                    case "pfad":
                        labyrinth42();
                    case "loch":
                        labyrinth33();
                    case "statue":
                        statue();
                    case "steintafel":
                        steintafel();
                    case "zauberbuch":
                        Zauberbuch();
                    case "feuerhand":
                        feuerhand();
                    case "eisstrahl":
                        eisstrahl();
                    case "steinmesser":
                        steinmesser();
                    case "feuerball":
                        feuerball();
                    case "leuchtorb":
                        leuchtorb();
                    case "adlerauge":
                        adlerauge();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();
                }
            case "steintafel":
                switch (command) {
                    case "gehen":
                        labyrinth41();
                    case "karte":
                        System.out.println("   RICHTIG!!!");
                        secretSteintafel();
                    default:
                        System.out.print("Falsch!");
                        playerInput();
                }
            case "labyrinth42":

                switch (command) {
                    case "weiter":
                        if (lichtinhöhle == 0) {
                            System.out.println("   Es ist zu dunkel um weiterzugehen!");
                        } else {
                            System.out.println(
                                    "   Mit dem Leuchtorb wanderst du weiter bis du auf eine Leiter triffst. Sie führt nach 'oben'.");
                            playerInput();
                        }
                        playerInput();
                    case "leuchtorb":
                        leuchtorb();
                    case "oben":
                        System.out.println(
                                "   Du entscheidest dich die Leiter die nach oben zu nehmen. Nach ein paar Metern starren dich hellgrüne Augen aus der Dunkelheit an. \n   W-Wer bist du? Ein Menschen-Ding-Ding? Ich tu weh wenn du nicht sagst. Antwortest!");
                        talkToSkaven();
                    case "unten":
                        skavenFight();
                    case "hinaus":
                        labyrinth41();
                    case "zauberbuch":
                        Zauberbuch();
                    case "feuerhand":
                        feuerhand();
                    case "eisstrahl":
                        eisstrahl();
                    case "steinmesser":
                        steinmesser();
                    case "feuerball":
                        feuerball();
                    case "adlerauge":
                        adlerauge();
                    case "kronen":
                        gold();
                    case "inventar":
                        inventar();
                    case "hilfe":
                        hilfe();
                    default:
                        System.out.print("Falscher Befehl!");
                        playerInput();

                }
            case "skaventalk":
                switch (command) {
                    case "angreifen":
                        System.out.print("   Du entscheidest dich den Skaven zu bekämpfen!");
                        playerInput();
                        skavenFight();
                    case "wegrennen":
                        System.out.print(
                                "   Du versuchst zu fliehen, doch es ist sinnlos. \n   \"WEGRENNEN-RENNEN! MENSCHEN-DING! GEFAHR!\"");
                        playerInput();
                        skavenFight();
                    case "leugnen":
                        System.out.print(
                                "   Du versuchst zu leugnen dass du ein Mensch bist, doch als der Skaven sich dir nähert erkennt er deine Wahre Gestalt. \n   \"WIDERLICHER MENSCHEN-DING! GEFAHR!\"");
                        playerInput();
                        skavenFight();
                    case "antworten":
                        System.out.print(
                                "   Du antwortest dass du ein Mensch bist. \n   \"Was suchen Menschen-Ding hier-hier? Großes, tolles Reich der Ratten ja-ja!\" \n   Mögliche Antworten: \n   Angreifen \n   Passieren");
                        switch (command) {
                            case "angreifen":
                                System.out.print("   Du entscheidest dich den Skaven zu bekämpfen!");
                                playerInput();
                                skavenFight();
                            case "passieren":
                                if (mauseRing == 1) {
                                    System.out.print(
                                            "   Die Ratte erkennt einen Ring an dir: \n   \"Mausering der heiligen-gen Ratte! Du passieren Ja-Ja!");
                                    ending1();
                                } else {
                                    System.out.print(
                                            "   \"Nein-Nein! Gefahr! Menschen Ding-Ding kaputtmachen! Zerstören! Ja-Ja!");
                                    playerInput();
                                    skavenFight();
                                }
                        }
                    case "nichts tun":
                        System.out.print(
                                "   Du antwortest und bewegst dich nicht. Die Kreatur kommt dir langsam näher. \n   \"Ein Menschen-Ding! Was tun Menschen-Ding hier-hier? Großes, tolles Reich der Ratten ja-ja! Angriff!");
                        skavenFight();
                }

        }
    }

    // Das Zauberbuch wird aufgerufen
    public void Zauberbuch() {
        System.out.print("   Diese Zauber hast du erlernt: ");
        for (int i = 0; i < spellbook.size(); i++) {
            System.out.print(spellbook.get(i));
            if (i < spellbook.size() - 1) {
                System.out.print(", ");
            }
        }
        playerInput();
    }

    // Ab hier beginnen die Zauber
    public void feuerhand() {
        switch (position) {
            case "zimmer":
                System.out.println("   Du benutzt deine Feuerhände. Warum?");
                playerInput();
            case "korridor":
                System.out.println(
                        "   Du aktivierst die Feuerhand auf dem Korridor und zündest absichtlich einen vorbeilaufenden Studenten die Haare an. GAME OVER!");
                startmenü();
            case "saal":
                System.out.println("   Hier wirst du definitiv nicht so etwas tun.");
                playerInput();
            case "garten":
                System.out.println("   Hier wirst du definitiv nicht so etwas tun.");
                playerInput();
            case "goblinfight":
                System.out.println("   Du benutzt Feuerhand gegen den Goblin!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(12, 4);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Goblin " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            case "labyrinth10":
                System.out.println(
                        "   Du benutzt deine Feuerhand und hältst es gegen die Hecke. Auch wenn es scheint als würde sie brennen, ist das Feuer auch schon bald wieder aus und die Hecke ohne Schaden.");
                playerInput();
            case "labyrinth24":
                System.out.println("   Der Zauber funktioniert an der Tür leider nicht.");
                playerInput();
            case "labyrinth33":
                System.out.println(
                        "   Deine Feuerhände verbrennen den Schwachpunkt und ein Weg offenbart sich.");
                heckeschwachpunkt = 1;
                labyrinth33();
            case "feefight":
                System.out.println(
                        "   Die Fee ist nicht nah genug dran für diesen Zauber, sie weicht ihm ohne Probleme aus!");
                monsterAttack();
            default:
                System.out.print("Falscher Befehl!");
                playerInput();
            case "skavenfight":
                System.out.println("   Du benutzt Feuerhand gegen den Skaven!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(12,2);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Skaven " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
        }
    }

    // Pew Pew
    public void eisstrahl() {
        switch (position) {
            case "zimmer":
                System.out.println(
                        "   Du schießt einen Eisstrahl in deinem Zimmer los, es trifft die Wand und friert sie ein. Du fragst dich: Warum tue ich das?");
                playerInput();
            case "korridor":
                System.out.println(
                        "   Du schießt einen Eisstrahl im Korridor los und friest das Bein eines armen Studenten ein. GAME OVER!");
                startmenü();
            case "saal":
                System.out.println("   Hier wirst du definitiv nicht so etwas tun.");
                playerInput();
            case "garten":
                System.out.println("   Hier wirst du definitiv nicht so etwas tun.");
                playerInput();
            case "goblinfight":
                System.out.println("   Du benutzt Eisstrahl gegen den Goblin!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg =getRandomNum(11,1);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Goblin " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            case "labyrinth10":
                System.out.println("   Du wüsstest nicht was du jetzt mit einem Eisstrahl machen solltest.");
                playerInput();
            case "labyrinth24":
                System.out.println("   Der Zauber funktioniert an der Tür leider nicht.");
                playerInput();
            case "labyrinth33":
                System.out.println("   Das ist nicht der richtige Zauber.");
                playerInput();
            case "labyrinth42":
                System.out.print(
                        "   Du setzt den Eisstrahl ein um den Raum heller zu machen. Es funktioniert logischerweise nicht. Du bist ein Clown und fühlst dich dumm.");
            case "feefight":
                System.out.println("   Du schießt einen Eisstrahl auf die Fee. Es ist effektiv!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(12, 4);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast der Fee " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            case "skavenfight":
                System.out.println("   Du benutzt Eisstrahl gegen den Skaven! Es ist effektiv!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(12, 4);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Skaven " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            default:
                System.out.println("Falscher Befehl!");
                playerInput();

        }
    }

    // Tipp: Wirf es auf das Fenster
    public void steinmesser() {
        switch (position) {
            case "zimmer":
                if (steinmesserzimmer == 0) {
                    System.out.println(
                            "   Du wirfst ein Steinmesser auf dein Fenster und machst es kaputt. Das Fenster ist traurig.");
                    steinmesserzimmer = 1;
                } else {
                    System.out.println("Dein Fenster ist doch schon kaputt du Monster!");
                }
                playerInput();
            case "korridor":
                System.out.println(
                        "   Du entscheidest dich lieber nicht ein Messer in einem Korridor voller Leute herumzuschießen");
                playerInput();
            case "saal":
                System.out.println("   Hier wirst du definitiv nicht so etwas tun.");
                playerInput();
            case "garten":
                System.out.println("   Hier wirst du definitiv nicht so etwas tun.");
                playerInput();
            case "goblinfight":
                System.out.println("   Du benutzt Steinmesser gegen den Goblin!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(12,2);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Goblin " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            case "feefight":
                System.out.println("   Du schießt ein Steinmesser auf die Fee. Es ist effektiv!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(12,2);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast der Fee " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            case "labyrinth10":
                System.out.println("   Es gibt gerade nichts, was du mit dem Steinmesser anfangen könntest.");
                playerInput();
            case "labyrinth24":
                System.out.println("   Der Zauber funktioniert an der Tür leider nicht.");
                playerInput();
            case "labyrinth33":
                System.out.println("   Das ist nicht der richtige Zauber.");
                playerInput();
            case "skavenfight":
                System.out.println("   Du schießt ein Steinmesser auf den Skaven!");
                playerHitAC = getRandomNum(20, 0);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(12,2);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Skaven " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            case "labyrinth41":
                System.out.println("   Das Steinmesser funktioniert gerade nicht.");
                labyrinth41();
            default:
                System.out.println("Falscher Befehl!");
                playerInput();
        }
    }

    // Donner macht aua
    public void donner() {
        switch (position) {
            case "feefight":
                System.out.println("   Du schießt einen Donner auf die Fee. Es ist sehr effektiv!");
                playerHitAC = getRandomNum(20,1);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(14,4);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast der Fee " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
                monsterAttack();
            case "labyrinth41":
                System.out.println(
                        "   Du schießt einen Donner los. Es war extrem laut. \n\"Sag emol, bisch du bescheuert? Du kasch doch ned ane Donner mache, du Voger!\"");
                labyrinth41();
            case "skavenfight":
                System.out.println("   Du schießt einen Donner auf den Skaven. Es ist sehr effektiv!");
                playerHitAC = getRandomNum(20,1);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(13,3);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Skaven " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
        }

    }

    // Damit soll man mögliche Inputs (auch geheime) sehen dürfen
    public void adlerauge() {
        switch (position) {
            case "zimmer":
                System.out.println("Schreibtisch, Regal, Glaskugel, Fenster, Stab, Tor, Wandteppiche");
                playerInput();

            case "labyrinth41":
                System.out.println("   !!!Leuchtorb!!!, Statue, Steintafel, Pfad");
        }
    }

    // Damit soll die Höhle erleuchtet werden (Idee im Kampf: Gegner blenden und die
    // Chance zum treffen senken, vielleicht bei einem Gegner der einen Angriff
    // auflädt)
    public void leuchtorb() {
        switch (position) {
            case "labyrinth41":
                System.out.println(
                        " Der Leuchtorb schwebt durch den Raum. Er erhellt die Steintafel. Dadurch enthüllt sich ein einziger Buchstabe: K \n Es scheint als würde die Antwort auf das Rästel mit dem Buchstaben K beginnen");
                playerInput();
            case "labyrinth42":
                System.out.println(
                        "   Der Leuchtorb beginnt zu schweben und die Höhle auszuleuchten. Du kannst nun weiter!");
                lichtinhöhle = 1;
                labyrinth42();
        }
    }

    // FIREBALL
    public void feuerball() {
        switch (position) {
            case "labyrinth41":
                System.out.println("   Du benutzt den Feuerball lieber nicht, denn man spielt nicht mit Feuer.");
                playerInput();
            case "skavenfight":
                System.out.println("   Du schießt einen Feuerball auf den Skaven. Es ist sehr effektiv!");
                playerHitAC = getRandomNum(20,1);
                if (playerHitAC > monsterAC) {
                    playerDmg = getRandomNum(14,4);
                    monsterHP = monsterHP - playerDmg;
                    System.out.println("   Du hast dem Skaven " + playerDmg + " Schaden zugefügt!");
                } else {
                    System.out.println("   Du hast verfehlt!");
                }
                ;
            default:
                System.out.println("Das geht hier nicht!");
                playerInput();
        }
    }

    // Kleid und Statue
    public void ending1() {
        System.out.print(
                "   An dem Skaven vorbei entspannst du dich für einen Moment und überlegst, was du alles erlebt hast. \n   Du gehst den Weg weiter und siehst schon bald Licht. Du läufst aus der Höhle heraus und siehst einen deiner Lehrmeister mit einem zufriedenen Blick. Du hast es geschafft. \n   Glückwunsch!  Du hast das Labyrinth bewältigt und bist nun offiziell ein Magier!");
    }
}