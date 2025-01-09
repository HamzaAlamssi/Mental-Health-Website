package com.example.MentalHealthSystem.constants;

import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class AssessmentInfo {
    private static final Map<String, Integer> NUM_QUESTIONS = new HashMap<>();

    static {
        NUM_QUESTIONS.put("anxiety", 7);
        NUM_QUESTIONS.put("depression", 9);
        NUM_QUESTIONS.put("self-esteem", 10);
        NUM_QUESTIONS.put("social shyness", 15);
        NUM_QUESTIONS.put("obsessive compulsive", 13);
        NUM_QUESTIONS.put("burnout", 15);
        NUM_QUESTIONS.put("narcissistic personality", 9);
    }

    private static final Set<String> ANXIETY_QUESTIONS = new HashSet<>();
    static {
        ANXIETY_QUESTIONS.add("Feeling nervous, anxious, or on edge");
        ANXIETY_QUESTIONS.add("Not being able to stop or control worrying");
        ANXIETY_QUESTIONS.add("Worrying too much about different things");
        ANXIETY_QUESTIONS.add("Trouble relaxing");
        ANXIETY_QUESTIONS.add("Being so restless that it is hard to sit still");
        ANXIETY_QUESTIONS.add("Becoming easily annoyed or irritable");
        ANXIETY_QUESTIONS.add("Feeling afraid, as if something awful might happen");

    }
    private static final Set<String> BURNOUT_QUESTIONS = new HashSet<>();
    static {
        BURNOUT_QUESTIONS.add("I feel run down and drained of physical or emotional energy.");
        BURNOUT_QUESTIONS.add("I have negative thoughts about my job.");
        BURNOUT_QUESTIONS.add("I am harder and less sympathetic with people than perhaps they deserve.");
        BURNOUT_QUESTIONS.add("I am easily irritated by small problems, or by my co-workers and team.");
        BURNOUT_QUESTIONS.add("I feel misunderstood or unappreciated by my co-workers.");
        BURNOUT_QUESTIONS.add("I feel that I have no one to talk to.");
        BURNOUT_QUESTIONS.add("I feel that I am achieving less than I should.");
        BURNOUT_QUESTIONS.add("I feel under an unpleasant level of pressure to succeed.");
        BURNOUT_QUESTIONS.add("I feel that I am not getting what I want out of my job.");
        BURNOUT_QUESTIONS.add("I feel that I am in the wrong organization or the wrong profession.");
        BURNOUT_QUESTIONS.add("I am frustrated with parts of my job.");
        BURNOUT_QUESTIONS.add("I feel that organizational politics or bureaucracy frustrate my ability to do a good job.");
        BURNOUT_QUESTIONS.add("I feel that there is more work to do than I practically have the ability to do.");
        BURNOUT_QUESTIONS.add("I feel that I do not have time to do many of the things that are important to doing a good quality job.");
        BURNOUT_QUESTIONS.add("I find that I do not have time to plan as much as I would like to.");

    }
    private static final Set<String> DEPRESSION_QUESTIONS = new HashSet<>();
    static {
        DEPRESSION_QUESTIONS.add("Little interest or pleasure in doing things.");
        DEPRESSION_QUESTIONS.add("Feeling down, depressed, or hopeless.");
        DEPRESSION_QUESTIONS.add("Trouble falling or staying asleep, or sleeping too much.");
        DEPRESSION_QUESTIONS.add("Feeling tired or having little energy.");
        DEPRESSION_QUESTIONS.add("Poor appetite or overeating.");
        DEPRESSION_QUESTIONS.add("Feeling bad about yourself - or that you are a failure or have let yourself or your family down.");
        DEPRESSION_QUESTIONS.add("Trouble concentrating on things, such as reading the newspaper or watching television.");
        DEPRESSION_QUESTIONS.add("Moving or speaking so slowly that other people could have noticed\n" +
                "        Or the opposite - being so fidgety or restless that you have been moving around a lot more than usual.");
        DEPRESSION_QUESTIONS.add("Thoughts that you would be better off dead, or of hurting yourself.");

    }
    private static final Set<String> NARCISSISTIC_QUESTIONS = new HashSet<>();
    static {
        NARCISSISTIC_QUESTIONS.add("Do you experience an exaggerated sense of self-importance that frequently involves the need to exaggerate your talents or accomplishments?");
        NARCISSISTIC_QUESTIONS.add("Do you believe you are special and unique and can only be understood by, or should associate with, other special or high-status people or institutions?");
        NARCISSISTIC_QUESTIONS.add("Do you find that you constantly have a willingness to take advantage of others to achieve your own goals?");
        NARCISSISTIC_QUESTIONS.add("Do you require excessive admiration from others?");
        NARCISSISTIC_QUESTIONS.add("Are you preoccupied with fantasies of unlimited success, power, brilliance, beauty, or ideal love?");
        NARCISSISTIC_QUESTIONS.add(" Do you have a sense of entitlement from others that involve unreasonable expectations of especially favorable treatment or automatic compliance with their expectations?");
        NARCISSISTIC_QUESTIONS.add("Do you find you are unwilling to recognize or identify with the feelings and needs of others?");
        NARCISSISTIC_QUESTIONS.add("Do others perceive you as arrogant or snobby?");
        NARCISSISTIC_QUESTIONS.add("Do you find that you are often envious of others and/or believe that others are envious of you?");

    }
    private static final Set<String> OBSESSIVECOMPULSIVE_QUESTIONS = new HashSet<>();
    static {
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you worry about germs, getting sick, or dying?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you have extreme fears about bad things happening or doing something wrong?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you feel that things have to be “just right”?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you have disturbing and unwanted thoughts or images about hurting others, but don’t want to hurt anyone?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you have disturbing and unwanted thoughts or images of a sexual nature?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you worry a lot about doing the wrong thing?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Are you bothered by thoughts that you find embarrassing or gross?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you engage in excessive checking (re-checking that the door is locked, that the oven is off)?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you engage in excessive washing and/or cleaning?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you repeat actions until they are “just right” or start things over again?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you order or arrange things or items?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you need excessive reassurance (e.g., always asking, “Are you sure I’m going to be okay?”)?");
        OBSESSIVECOMPULSIVE_QUESTIONS.add("Do you spend more than 3 hours total a day struggling with obsessions or compulsions?");

    }
    private static final Set<String> SELF_ESTEEM_QUESTIONS = new HashSet<>();
    static {
        SELF_ESTEEM_QUESTIONS.add("Do you believe you are inferior to others in some way?");
        SELF_ESTEEM_QUESTIONS.add("Do you believe you are a worthwhile person?");
        SELF_ESTEEM_QUESTIONS.add("Are you sensitive to criticism?");
        SELF_ESTEEM_QUESTIONS.add("Do you feel like an overall failure?");
        SELF_ESTEEM_QUESTIONS.add("How often do you think positively about yourself?");
        SELF_ESTEEM_QUESTIONS.add("Do you like and accept yourself even when you are rejected by others?");
        SELF_ESTEEM_QUESTIONS.add("Do you get nervous when called upon to speak to a group of people you don't know?");
        SELF_ESTEEM_QUESTIONS.add("Are you satisfied with the person you've become?");
        SELF_ESTEEM_QUESTIONS.add("Do you feel good about yourself without regular validation from others?");
        SELF_ESTEEM_QUESTIONS.add("How would you rate your level of self-esteem?");
    }

    private static final Set<String> SOCIALSHYNESS_QUESTIONS = new HashSet<>();
    static {
        SOCIALSHYNESS_QUESTIONS.add("I feel shy in a social situation.");
        SOCIALSHYNESS_QUESTIONS.add("I tend to blush or feel my face getting warm around unfamiliar people.");
        SOCIALSHYNESS_QUESTIONS.add("I find it difficult to initiate conversations with strangers.");
        SOCIALSHYNESS_QUESTIONS.add("I can’t open up in social events or gatherings.");
        SOCIALSHYNESS_QUESTIONS.add("I feel over-conscious when being the center of attention.");
        SOCIALSHYNESS_QUESTIONS.add("I prefer to stay quiet and observe others in a group setting.");
        SOCIALSHYNESS_QUESTIONS.add("I find it difficult to make eye contact with others.");
        SOCIALSHYNESS_QUESTIONS.add("I struggle to speak up and express my opinions, especially when I disagree with someone.");
        SOCIALSHYNESS_QUESTIONS.add("I have a tendency to overthink or analyze deeply if someone gives me any feedback.");
        SOCIALSHYNESS_QUESTIONS.add("I find it difficult to ask people for any information.");
        SOCIALSHYNESS_QUESTIONS.add("I feel uncomfortable with physical touch, such as greetings or hugging, when I meet people.");
        SOCIALSHYNESS_QUESTIONS.add("I find it difficult to talk about myself in front others.”)?");
        SOCIALSHYNESS_QUESTIONS.add("I feel uncomfortable to talk to people of opposite sex.");
        SOCIALSHYNESS_QUESTIONS.add("I prefer to have a few close friends rather than a larger social circle.");
        SOCIALSHYNESS_QUESTIONS.add("I struggle to involve in close and intimate relationships with another person.");

    }
    private static final Map<String, Set<String>> ASSESSMENT_QUESTIONS = new HashMap<>();

    static {
        ASSESSMENT_QUESTIONS.put("anxiety", ANXIETY_QUESTIONS);
        ASSESSMENT_QUESTIONS.put("burnout", BURNOUT_QUESTIONS);
        ASSESSMENT_QUESTIONS.put("self-esteem", SELF_ESTEEM_QUESTIONS);
        ASSESSMENT_QUESTIONS.put("social shyness", SOCIALSHYNESS_QUESTIONS);
        ASSESSMENT_QUESTIONS.put("obsessive compulsive", OBSESSIVECOMPULSIVE_QUESTIONS);
        ASSESSMENT_QUESTIONS.put("depression", DEPRESSION_QUESTIONS);
        ASSESSMENT_QUESTIONS.put("narcissistic", NARCISSISTIC_QUESTIONS);
    }

    public static int getNumberOfQuestions(String assessmentName) {
        assessmentName = assessmentName.toLowerCase(Locale.ROOT);
        if (NUM_QUESTIONS.containsKey(assessmentName)) {
            return NUM_QUESTIONS.get(assessmentName);
        } else {
            throw new IllegalArgumentException("Assessment name not recognized: " + assessmentName);
        }
    }



    public static Set<String> getAssessmentQuestions(String assessmentName) {
        assessmentName = assessmentName.toLowerCase(Locale.ROOT);
        if (ASSESSMENT_QUESTIONS.containsKey(assessmentName)) {
            return ASSESSMENT_QUESTIONS.get(assessmentName);
        } else {
            throw new IllegalArgumentException("Assessment name not recognized: " + assessmentName);
        }
    }
}

