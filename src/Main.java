import com.exercise.jenkins.JenkinsExercise;

public class Main {
    public static void main(String[] args) {
        String name = System.getenv("NAME");
        String number = System.getenv("NUMBER");

        JenkinsExercise jenkinsExercise = new JenkinsExercise();
        jenkinsExercise.printExercise(name, number);
    }
}