package test;

import model.SaltGenerator;

public class SaltGeneratorTest {

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            String salt = SaltGenerator.generate();
            System.out.println(""+salt);
        }
//        System.out.println(HashHelper.hash("asdfasdfasdf"));
//        System.out.println(HashHelper.hash("sdfasdf"));
//        System.out.println(HashHelper.hash(""));
    }

}
