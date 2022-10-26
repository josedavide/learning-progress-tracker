package tracker;


public class Main {

    public static void main(String[] args) {
        try {
            LearningPlatformRepository learningRepository = new LearningPlatformRepository();
            LearningPlatformController learningPlatformController = new LearningPlatformController(learningRepository);
            learningPlatformController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
