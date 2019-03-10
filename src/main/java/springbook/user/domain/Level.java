package springbook.user.domain;

/**
 * Created by adaeng on 10/03/2019.
 *
 *  슷자 타입을 직접 제공하는 것보다는 enum을 사용하는 것이 훨씬 안정적
 *
 */
public enum Level {
    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    Level(int value){
        this.value = value;
    }

    public int intValue(){
        return value;
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
