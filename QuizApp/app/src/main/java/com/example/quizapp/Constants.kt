package com.example.quizapp

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val que1 = Question(1,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            optionOne = "Argentina",
            optionTwo = "Australia",
            optionThree = "Armenia",
            optionFour = "Austria",
            correctAnswer = 1)

        questionsList.add(que1)

        val que2 = Question(2,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            optionOne = "Argentina",
            optionTwo = "Australia",
            optionThree = "Armenia",
            optionFour = "Austria",
            correctAnswer = 2)

        questionsList.add(que2)

        val que3 = Question(3,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            optionOne = "Belarus",
            optionTwo = "Belize",
            optionThree = "Brunei",
            optionFour = "Brazil",
            correctAnswer = 4)

        questionsList.add(que3)

        val que4 = Question(4,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_belgium,
            optionOne = "Bahamas",
            optionTwo = "Belgium",
            optionThree = "Barbados",
            optionFour = "Belize",
            correctAnswer = 2)

        questionsList.add(que4)

        val que5 = Question(5,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_fiji,
            optionOne = "Gabon",
            optionTwo = "France",
            optionThree = "Fiji",
            optionFour = "Finland",
            correctAnswer = 3)

        questionsList.add(que5)

        val que6 = Question(6,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_germany,
            optionOne = "Germany",
            optionTwo = "Georgia",
            optionThree = "Greece",
            optionFour = "none of these",
            correctAnswer = 1)

        questionsList.add(que6)

        val que7 = Question(7,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_denmark,
            optionOne = "Dominica",
            optionTwo = "Egypt",
            optionThree = "Denmark",
            optionFour = "Ethiopia",
            correctAnswer = 3)

        questionsList.add(que7)

        val que8 = Question(8,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_india,
            optionOne = "Ireland",
            optionTwo = "Iran",
            optionThree = "Hungary",
            optionFour = "India",
            correctAnswer = 4)

        questionsList.add(que8)

        val que9 = Question(9,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_new_zealand,
            optionOne = "Australia",
            optionTwo = "New Zealand",
            optionThree = "Tuvalu",
            optionFour = "United States of America",
            correctAnswer = 2)

        questionsList.add(que9)

        val que10 = Question(10,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_kuwait,
            optionOne = "Kuwait",
            optionTwo = "Jordan",
            optionThree = "Sudan",
            optionFour = "Palestine",
            correctAnswer = 1)

        questionsList.add(que10)

        return questionsList
    }
}