package arbre;

public class test {

    public static AB<Character> arbre1(){
        AB<Character> arbre1 = new AB('h',
                new AB('e',
                        new AB('v'),
                        new AB('s')),
                new AB('g',
                        new AB('f',
                                null,
                                new AB('u')
                        ),
                        new AB('t',
                                new AB('r'),
                                null
                                )
                        )
                );
        return arbre1;
    }

    public static AB<Character> arbre2(){
        AB<Character> arbre2 = new AB('a',
                new AB('b',
                        new AB('c'),
                        new AB('d',
                                new AB('e',
                                        new AB('g'),
                                        null
                                ),
                                new AB('f')
                                )
                        ),
                null
        );
        return arbre2;
    }

    public static ABR<Integer> arbre3(){
        ABR<Integer> arbre3 = new ABR(77,
                new ABR(66,
                        new ABR(14,
                                new ABR(1),
                                new ABR(46,
                                        new ABR(39),
                                        new ABR(62,
                                                new ABR(51,
                                                        null,
                                                        new ABR(60)
                                                ),
                                                new ABR(63)
                                        )
                                )

                        ),
                        new ABR(69)
                ),
                new ABR(81,
                        null,
                        new ABR(84,
                                null,
                                new ABR(93,
                                        new ABR(92),
                                        null))
                )
        );
        return arbre3;
    }

    public static void main(String[] args) {
        System.out.println(arbre3().contains2(84));
        
    }









}
