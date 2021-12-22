package ru.larina;

import ru.larina.annotation.AutoInjectable;
import ru.larina.interfaces.Interface1V;
import ru.larina.interfaces.Interface2V;

public class Bean {

    @AutoInjectable
    private Interface1V inject1;
    @AutoInjectable
    private Interface2V inject2;

    private String str1;
    private String str2;
    private String str3;

    public void foo() {
        inject1.do1V();
        inject2.do2V();
    }
}
