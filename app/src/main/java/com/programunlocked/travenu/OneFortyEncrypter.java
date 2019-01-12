package com.programunlocked.travenu;

/**
 * Created by jchac on 26-01-2018.
 */
public class OneFortyEncrypter {

    private static final String A = "ZZ";
    private static final String B = "YY";
    private static final String C = "XX";
    private static final String D = "WW";
    private static final String E = "VV";
    private static final String F = "UU";
    private static final String G = "TT";
    private static final String H = "SS";
    private static final String I = "RR";
    private static final String J = "QQ";
    private static final String K = "PP";
    private static final String L = "OO";
    private static final String M = "NN";
    private static final String N = "MM";
    private static final String O = "LL";
    private static final String P = "KK";
    private static final String Q = "JJ";
    private static final String R = "II";
    private static final String S = "HH";
    private static final String T = "GG";
    private static final String U = "FF";
    private static final String V = "EE";
    private static final String W = "DD";
    private static final String X = "CC";
    private static final String Y = "BB";
    private static final String Z = "AA";

    private static final String TA = "139";
    private static final String RM = "1230";
    private static final String AS = "230";
    private static final String RT = "1312";

    private static final String RW = "RW";

    private static final String ONE = "ENO";
    private static final String TWO = "OWT";
    private static final String THREE = "EERHT";
    private static final String FOUR = "RUOF";
    private static final String FIVE = "EVIF";
    private static final String SIX = "XIS";
    private static final String SEVEN = "NEVES";
    private static final String EIGTH = "HTGIE";
    private static final String NINE = "ENIN";
    private static final String ZERO = "OREZ";



    //Members Needed To Perform Encryption
    private String encryptedPass = "";
    private int passwordLength=0;

    public OneFortyEncrypter(){
        //Empty

    }


    public String encrypt(String password)
    {
        encryptedPass="";

        char currentLetter = 'A';
        char previousLetter = 'A';
        boolean inBetween = false;

        password = password.toUpperCase();

        passwordLength = password.length();
        for(int i =0; i<passwordLength; i++)
        {
            currentLetter = password.charAt(i);
            if(i>0)
            {
                previousLetter = password.charAt(i-1);
                inBetween = true;
            }
            switch (currentLetter)
            {
                case 'A':
                    encryptedPass = encryptedPass+A;
                    encryptedPass = encryptedPass+TA;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+B+RW;
                        }
                    }
                    break;
                case 'B':
                    encryptedPass = encryptedPass+B;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+C+RW;
                        }
                    }
                    break;
                case 'C':
                    encryptedPass = encryptedPass+C;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+D+RW;
                        }
                    }
                    break;
                case 'D':
                    encryptedPass = encryptedPass+D;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+E+RW;
                        }
                    }
                    break;
                case 'E':
                    encryptedPass = encryptedPass+E;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+F+RW;
                        }
                    }
                    break;
                case 'F':
                    encryptedPass = encryptedPass+F;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+G+RW;
                        }
                    }
                    break;
                case 'G':
                    encryptedPass = encryptedPass+G;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+H+RW;
                        }
                    }
                    break;
                case 'H':
                    encryptedPass = encryptedPass+H;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+I+RW;
                        }
                    }
                    break;
                case 'I':
                    encryptedPass = encryptedPass+I;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+J+RW;
                        }
                    }
                    break;
                case 'J':
                    encryptedPass = encryptedPass+J;
                    if(inBetween)
                    {
                        if(previousLetter==currentLetter)
                        {
                            encryptedPass = encryptedPass+K+RW;
                        }
                    }
                    break;
                case 'K':
                    encryptedPass = encryptedPass+K;
                    break;
                case 'L':
                    encryptedPass = encryptedPass+L;
                    break;
                case 'M':
                    encryptedPass = encryptedPass+M;
                    break;
                case 'N':
                    encryptedPass = encryptedPass+N;
                    break;
                case 'O':
                    encryptedPass = encryptedPass+O;
                    break;
                case 'P':
                    encryptedPass = encryptedPass+P;
                    break;
                case 'Q':
                    encryptedPass = encryptedPass+Q;
                    break;
                case 'R':
                    encryptedPass = encryptedPass+R;
                    encryptedPass = encryptedPass+RT;
                    break;
                case 'S':
                    encryptedPass = encryptedPass+S;
                    encryptedPass = encryptedPass+RM;
                    break;
                case 'T':
                    encryptedPass = encryptedPass+T;
                    encryptedPass = encryptedPass+AS;
                    break;
                case 'U':
                    encryptedPass = encryptedPass+U;
                    break;
                case 'V':
                    encryptedPass = encryptedPass+V;
                    break;
                case 'W':
                    encryptedPass = encryptedPass+W;
                    break;
                case 'X':
                    encryptedPass = encryptedPass+X;
                    break;
                case 'Y':
                    encryptedPass = encryptedPass+Y;
                    break;
                case 'Z':
                    encryptedPass = encryptedPass+Z;
                    break;
                case 1:
                    encryptedPass = encryptedPass+ONE+A;
                    break;
                case 2:
                    encryptedPass = encryptedPass+TWO+C;
                    break;
                case 3:
                    encryptedPass = encryptedPass+THREE+E;
                    break;
                case 4:
                    encryptedPass = encryptedPass+FOUR+G;
                    break;
                case 5:
                    encryptedPass = encryptedPass+FIVE+I;
                    break;
                case 6:
                    encryptedPass = encryptedPass+SIX+K;
                    break;
                case 7:
                    encryptedPass = encryptedPass+SEVEN+M;
                    break;
                case 8:
                    encryptedPass = encryptedPass+EIGTH+O;
                    break;
                case 9:
                    encryptedPass = encryptedPass+NINE+Q;
                    break;
                case 0:
                    encryptedPass = encryptedPass+ZERO+S;
                    break;
                default:
                    encryptedPass = encryptedPass+currentLetter;

            }
        }

        return encryptedPass;
    }
}
