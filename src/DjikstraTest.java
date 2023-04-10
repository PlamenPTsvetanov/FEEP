import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DjikstraTest {
    private Dijkstra d = new Dijkstra();
    @Test
    public void _1_parentheses() {
       assertEquals(d.task("a+b*(c-d+e)"), "abcd-e+*+");
    }

    @Test
    public void _2_power() {
        assertEquals(d.task("a^b^c^d"), "abcd^^^");
    }

    @Test
    public void _3_no_special() {
        assertEquals(d.task("a-b*c^d"), "abcd^*-");
    }

    @Test
    public void _4_swapped_priorities() {
        assertEquals(d.task("a*b+c"), "ab*c+");
        assertEquals(d.task("a+b*c"), "abc*+");
    }

    @Test
    public void _5_complex() {
        assertEquals(d.task("(a+b^c^d)/(e-f)"), "abcd^^+ef-/");
    }

    @Test
    public void _6_one_equals() {
        assertEquals(d.task("x=a+b*c-d"), "x'abc*+d-=");
    }
    @Test
    public void _7_with_two_equals() {
        assertEquals(d.task("x=y=a*b/c+d"), "x'y'ab*c/d+==");
    }
}