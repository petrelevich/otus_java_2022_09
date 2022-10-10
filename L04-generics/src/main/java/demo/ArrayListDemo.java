package demo;

import demo.generics.bounds.entries.Animal;
import demo.generics.bounds.entries.Cat;
import demo.generics.bounds.entries.HomeCat;
import demo.generics.bounds.entries.WildCat;

import java.util.*;

public class ArrayListDemo {
    public static void main( String[] args ) {

        Cat[] animalArr = new Cat[]{ new HomeCat( "Мурзик" ), new HomeCat( "Васька" ) };
        List<Cat> animalList = Arrays.asList( animalArr );
        System.out.println( animalList );

        animalArr[ 0 ] = new HomeCat( "1" );
        System.out.println( animalList );

        //animalList.add(new HomeCat("Мурка")); //Ошибка

        Animal[] catsArr = animalList.toArray( new Animal[ 0 ] );
        System.out.println( "catsArr:" + Arrays.toString( catsArr ) );

        //copy(List<? super T> dest, List<? extends T> src)
        List<? super Cat> animalListDest = new ArrayList<>( animalList );
        Collections.copy( animalListDest, animalList );
        System.out.println( "homeCats:" + animalListDest );


        //Как убрать дубли
        List<String> strDubl = Arrays.asList( "1", "2", "2", "4" );
        System.out.println( "srtDubl:" + strDubl );
        Set<String> strDublFiltered = new HashSet<>( strDubl );
        System.out.println( "strDublFiltered:" + strDublFiltered );

        //АвтоСортировка
        Set<Integer> sorted = new TreeSet<>();
        sorted.add( 1 );
        sorted.add( 9 );
        System.out.println( "sorted_1:" + sorted );
        sorted.add( 2 );
        sorted.add( 8 );
        System.out.println( "sorted_2:" + sorted );

        //
        List<Cat> newCats = new ArrayList<>();
        newCats.add( new WildCat( "pantera" ) );

        List<Cat> superCats = new ArrayList<>( newCats );
        Collections.copy( superCats, newCats );

        WildCat p = (WildCat) superCats.get( 0 );
        p.setName( "leon" );

        System.out.println( "New Cat :" + newCats.get( 0 ).toString() );


    }
}
