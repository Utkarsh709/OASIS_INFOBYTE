import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp {
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();
    private static List<MCQ> mcqs = new ArrayList<>();
    private static Session currentSession;

    public static void main(String[] args) {
        // Add some users
        users.add(new User("user1", "password1", "User One Profile Info"));
        users.add(new User("user2", "password2", "User Two Profile Info"));

        // Add some MCQs
        mcqs.add(new MCQ("q1", "What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, "Paris"));
        mcqs.add(new MCQ("q2", "What is 2 + 2?", new String[]{"3", "4", "5", "6"}, "4"));

        login();
        if (currentSession != null && currentSession.isActive()) {
            showMenu();
        }
    }

    private static void login() {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                currentSession = new Session(user);
                System.out.println("Login successful. Welcome, " + userId + "!");
                return;
            }
        }
        System.out.println("Invalid user ID or password.");
    }

    private static void showMenu() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nMenu:");
            System.out.println("1. Update Profile");
            System.out.println("2. Change Password");
            System.out.println("3. Start Quiz");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    startQuiz();
                    break;
                case 4:
                    logout();
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void updateProfile() {
        System.out.print("Enter new profile information: ");
        String profileInfo = scanner.nextLine();
        currentSession.getUser().updateProfile(profileInfo);
        System.out.println("Profile updated successfully.");
    }

    private static void changePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentSession.getUser().updatePassword(newPassword);
        System.out.println("Password changed successfully.");
    }

    private static void startQuiz() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Auto-submitting your answers...");
                submitQuiz();
            }
        }, 60000); // 1-minute timer for the quiz

        for (MCQ mcq : mcqs) {
            System.out.println("\n" + mcq.getQuestion());
            String[] options = mcq.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.print("Select an option (1-4): ");
            int answer = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (answer >= 1 && answer <= 4) {
                currentSession.getUser().saveAnswer(mcq.getQuestionId(), options[answer - 1]);
            } else {
                System.out.println("Invalid option. Skipping question.");
            }
        }

        timer.cancel();
        submitQuiz();
    }

    private static void submitQuiz() {
        int correctAnswers = 0;
        for (MCQ mcq : mcqs) {
            String userAnswer = currentSession.getUser().getAnswers().get(mcq.getQuestionId());
            if (mcq.isCorrect(userAnswer)) {
                correctAnswers++;
            }
        }
        System.out.println("Quiz submitted. You answered " + correctAnswers + " out of " + mcqs.size() + " questions correctly.");
    }

    private static void logout() {
        currentSession.endSession();
        System.out.println("Session closed. Logout successful.");
    }
}

class User {
    private String userId;
    private String password;
    private String profileInfo;
    private Map<String, String> answers;

    public User(String userId, String password, String profileInfo) {
        this.userId = userId;
        this.password = password;
        this.profileInfo = profileInfo;
        this.answers = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public void updateProfile(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void saveAnswer(String questionId, String answer) {
        answers.put(questionId, answer);
    }

    public Map<String, String> getAnswers() {
        return answers;
    }
}

class MCQ {
    private String questionId;
    private String question;
    private String[] options;
    private String correctAnswer;

    public MCQ(String questionId, String question, String[] options, String correctAnswer) {
        this.questionId = questionId;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equals(answer);
    }
}

class Session {
    private User user;
    private long startTime;
    private long endTime;
    private boolean isActive;

    public Session(User user) {
        this.user = user;
        this.startTime = System.currentTimeMillis();
        this.isActive = true;
    }

    public User getUser() {
        return user;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void endSession() {
        this.endTime = System.currentTimeMillis();
        this.isActive = false;
    }
}

