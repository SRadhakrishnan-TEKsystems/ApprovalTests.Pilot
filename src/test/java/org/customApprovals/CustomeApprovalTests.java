package org.customApprovals;

import org.CustomApproval;
import org.junit.jupiter.api.Test;
import org.legacybehavior.Person;

public class CustomeApprovalTests extends CustomApproval {
    @Test
    public void smoke() {
        Person actual = Person.getInstance("Peter Parker","Spider Man","MCU");
        Person existing = Person.getInstance("Peter Parker","Spider Man","Marvel");;
        approve(existing,actual);
    }
}
