package cinema

fun main() {

    class Cinema(val rows: Int, val seats: Int) {
        // Create a two-dimensional mutableList that holds all the rows as an own mutableList
        val cinemaSeatsAsList = MutableList(rows) { mutableListOf<String>() }
        val totalSeats = rows * seats
        var currentIncome = 0

        init {
            // For each row add an element 'S' depending on the number of seats
            for (i in 0..<rows) {
                for (j in 0..<seats) {
                    cinemaSeatsAsList[i].add("S")
                }
                // Add the number of the row to the beginning of the list
                cinemaSeatsAsList[i].add(0, (i + 1).toString())
            }

            // Add a mutable list that holds the number of seats in a row
            cinemaSeatsAsList.add(0, mutableListOf(" "))
            for (i in 1..seats) {
                cinemaSeatsAsList[0].add(i, i.toString())
            }
        }

        fun menu() {
            println("1. Show the seats")
            println("2. Buy a ticket")
            println("3. Statistics")
            println("0. Exit")

            val choice: Int = readln().toInt()
            println()

            when (choice) {
                1 -> printSeats(cinemaSeatsAsList)
                2 -> buyTicket()
                3 -> statistics()
                else -> return
            }
        }

        fun printSeats(cinemaSeatsAsList: MutableList<MutableList<String>>) {
            // Print out all the seats in the cinema
            println("Cinema:")
            for (row in cinemaSeatsAsList) println(row.joinToString(" "))
            println()
            menu()
        }

        fun buyTicket() {
            println("Enter a row number:")
            val chosenRow = readln().toInt()
            println("Enter a seat number in that row:")
            val chosenSeat = readln().toInt()

            if (chosenRow !in 1..rows || chosenSeat !in 1..seats) {
                println("Wrong input!")
                buyTicket()
            }

            if (markSeat(chosenRow, chosenSeat)) {
                val ticketPrice = calcPrice(chosenRow)
                println("Ticket price: $$ticketPrice")
                println("")
                currentIncome += ticketPrice
                printSeats(cinemaSeatsAsList)
                menu()
            } else {
                println("That ticket has already been purchased!")
                buyTicket()
            }
        }

        fun statistics() {
            var ticketsSold = 0

            for (i in 0..<cinemaSeatsAsList.size) {
                val n = cinemaSeatsAsList[i].count {it == "B"}
                ticketsSold += n
            }
            val percentageTickets: Double = ticketsSold.toDouble() / totalSeats * 100
            val formatPercentageTickets = "%.2f".format(percentageTickets)
            val totalIncome = calcProfit()

            println("Number of purchased tickets: $ticketsSold")
            println("Percentage: $formatPercentageTickets%")
            println("Current income: $$currentIncome")
            println("Total income: $$totalIncome")
            println("")
            menu()
        }

        fun markSeat(chosenRow: Int, chosenSeat: Int) : Boolean {
            // Check if the seat is available
            return if (cinemaSeatsAsList[chosenRow][chosenSeat] == "B") {
                false
            } else {
                // If available mark the chosen seat in the chosen row with the symbol 'B'
                cinemaSeatsAsList[chosenRow][chosenSeat] = "B"
                true
            }
        }

        fun calcPrice(chosenRow: Int): Int {
            return if (rows * seats <= 60) {
                10
            } else {
                if (chosenRow <= rows / 2) 10 else 8
            }
        }

        fun calcProfit(): Int {
            return if (rows * seats <= 60) {
                rows * seats * 10
            } else {
                if (rows % 2 != 0) {
                    (rows / 2) * seats * 10 + (rows / 2 + rows % 2) * seats * 8
                } else {
                    (rows / 2) * seats * 10 + (rows / 2) * seats * 8
                }
            }
        }

    }

    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    println()

    val cinema = Cinema(rows, seats)
    cinema.menu()
}

