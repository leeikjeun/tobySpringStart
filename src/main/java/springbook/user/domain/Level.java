package springbook.user.domain;

/**
 * Created by adaeng on 10/03/2019.
 *
 *  슷자 타입을 직접 제공하는 것보다는 enum을 사용하는 것이 훨씬 안정적
 *
 */
public enum Level {
    GOLD(3,null), SILVER(2,GOLD)  , BASIC(1,SILVER);

    private final int value;
    private final Level next;

    Level(int value, Level next){
        this.value = value;
        this.next = next;
    }

    public int intValue(){
        return value;
    }

    public Level getNextLevel(){
        return this.next;
    }

    public static Level valueOf(int value){
        switch (value){
            case 1 : return BASIC;
            case 2 : return SILVER;
            case 3 : return GOLD;
            default: throw new AssertionError("Unknown Level " + value);

        }
    }
}
