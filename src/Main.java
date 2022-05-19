import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Maria", "George", "Anna", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //количество несовершеннолетних
        long underage = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();

        //список фамилий призывников (т.е. мужчин от 18 до 27 лет невключительно)
        List<String> recruit = persons.stream()
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() >= 18 && person.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        //отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
        List<Person> worker = persons.stream()
                .filter(person -> person.getEducation().equals(Education.HIGHER) && person.getAge() >= 18)
                .filter(person -> person.getSex().equals(Sex.WOMAN) && person.getAge() < 60)
                .filter(person -> person.getSex().equals(Sex.MAN) && person.getAge() < 65)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
    }
}