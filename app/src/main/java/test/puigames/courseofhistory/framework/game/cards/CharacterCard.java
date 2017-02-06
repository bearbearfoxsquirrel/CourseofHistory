package test.puigames.courseofhistory.framework.game.cards;

import android.graphics.Bitmap;

/**
 * Created by Tommy on 23/11/2016.
 */

public class CharacterCard extends Card {
    //variables
    protected String name;
    protected String description;
    protected int mana;
    //protected Bitmap image;
    protected int attack;
    protected int health;
    protected String abilityDescription;

    public CharacterCard(Bitmap cardImage, float spawnX, float spawnY, String name, String description, int mana, int attack, int health, String abilityDescription)
    {
        super(cardImage, spawnX, spawnY);
        this.name = name;
        this.description = description;
        this.mana = mana;
        this.health = health;
        this.abilityDescription = abilityDescription;

    }


    //constructor
//    CharacterCard(String name, String CharDescription, int Mana, int Health, int Attack, String abilityDescription)
//    {
//        this.CharName = name;
//        this.CharDescription = CharDescription;
//        this.Mana = Mana;
//        //this.CharImage = Image;
//        this.Attack = Attack;
//        this.Health = Health;
//        this.AbilityDescription = abilityDescription;
//    }

    //method to initialise all cards as objects
//    public void initialiseAllCards(){
//        //Tier 1
//        //Mana 1
//       CharacterCard StudentJimmy = new CharacterCard("Student Jimmy","Absolutely Dying For a Boojum",1,1,1,"Charge");
//        CharacterCard StudentIvan = new CharacterCard("Student Ivan","My Mothers Name is also Ivan",1,1,1,"Taunt");
//        CharacterCard StudentSarah = new CharacterCard("Student Sarah","Got a bine m8?",1,2,1,"NONE");
//        //Mana 2
//        CharacterCard PhilipHanna = new CharacterCard("Philip Hanna","Make it rain!",2,3,1,"NONE");
//        CharacterCard HeadOfEnglish = new CharacterCard("Head Of English","Alliteration, assonance and fore-shadowing",2,2,2,"NONE");
//        CharacterCard GaelicCaptain = new CharacterCard("Gaelic Captain","I Play For County",2,1,2,"Charge");
//        CharacterCard LawLecturer = new CharacterCard("Law Lecturer","I'll see you in Court",2,2,1,"Taunt");
//        CharacterCard PresidentOfDragonslayer = new CharacterCard("President of Dragonslayer","M'Lady",2,1,3,"NONE");
//        CharacterCard MedicineLecturer = new CharacterCard("Medicine Lecturer","When I was growing up I wanted to be a doctor",1,2,1,"Boost Health(+2)");
//        //Mana 3
//        CharacterCard Counselor = new CharacterCard("Counselor","If you want to get better, you would have already",3,3,1,"Heal(+2");
//        CharacterCard Janitor = new CharacterCard("Janitor","This is easy just like drowning someone",3,1,3,"Charge");
//        CharacterCard Owner = new CharacterCard("Owner","Sure I can buy more land in Stranmillis",3,3,2,"NONE");
//        CharacterCard PersonalTutor = new CharacterCard("Personal Tutor","Once and never again",3,2,3,"NONE");
//        CharacterCard TheElmsBadger = new CharacterCard("The Elms Badger","I wanna eat your bins",3,2,2,"Boost Attack(+1)");
//        CharacterCard Chancellor = new CharacterCard("Chancellor","<insert generic boring speech>",3,3,1,"Taunt");
//
//        //Tier 2 - Great Minds
//        //Mana 4
//        CharacterCard AdaLovelace = new CharacterCard("Ada Lovelace","That brain of mine is something more than merely mortal",4,2,4,"NONE");
//        CharacterCard FlorenceNightingale = new CharacterCard("Florence Nightingale","I'd rather die in the surf than stand idly on the shore",4,4,1,"Boost Health(+2");
//        CharacterCard Plato = new CharacterCard("Plato","The measure of a man is what he does with power",4,4,2,"NONE");
//        CharacterCard Pythagoras = new CharacterCard("Pythagoras","Reason is immortal, all else mortal",4,2,3,"NONE");
//        CharacterCard Galileo = new CharacterCard("Galileo","By denying scientific principles, one may maintain any paradox",4,1,1,"Charge");
//        CharacterCard DavidAttenborough = new CharacterCard("David Attenborough","He's Blown It",4,3,3,"Taunt");
//        CharacterCard ThomasEdison = new CharacterCard("Thomas Edison","Everything comes to him who hustles while he waits",4,3,2,"Draw a Card");
//        CharacterCard LeonardoDaVinci = new CharacterCard("Leonardo Da Vinci","The smallest feline is a masterpiece",4,3,4,"NONE");
//
//        //Mana 5
//        CharacterCard ChristopherColumbus = new CharacterCard("Christopher Columbus","Following the light of the sun, we left the old world",5,5,2,"Taunt");
//        CharacterCard LudwigVanBeethoven = new CharacterCard("Ludwig Van Beethoven","I want to seize fate by the throat",5,2,5,"Charge");
//        CharacterCard BenjaminFranklin = new CharacterCard("Benjamin Franklin","You may delay but time will not.",5,3,4,"Inspire Health(+1)");
//        CharacterCard IsaacNewton = new CharacterCard("Isaac Newton","To every action there is an equal and opposite reaction",5,4,3,"Resultant Force");
//        CharacterCard AlanTuring = new CharacterCard("Turing","Machines take me by suprise with great frequency",5,5,2,"Taunt");
//        CharacterCard NikolaTesla = new CharacterCard("Nikola Tesla","The present is theirs; the future for which I really worked, is mine",5,2,5,"Inspire Attack (+1)");
//
//        //Mana 6
//        CharacterCard CharlesDarwin = new CharacterCard("Charles Darwin","The measure of a man is what he does with power",4,4,2,"NONE");
//        CharacterCard MartinLutherKing = new CharacterCard("Plato","The measure of a man is what he does with power",4,4,2,"NONE");
//        CharacterCard MadamCurie = new CharacterCard("Plato","The measure of a man is what he does with power",4,4,2,"NONE");
//        CharacterCard AlbertEinstein = new CharacterCard("Plato","The measure of a man is what he does with power",4,4,2,"NONE");
//        CharacterCard WillieShakespeare = new CharacterCard("Plato","The measure of a man is what he does with power",4,4,2,"NONE");
//        CharacterCard Ghandi = new CharacterCard("Plato","ma",4,4,2,"NONE");
//
//    }

}
