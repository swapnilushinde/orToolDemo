import java.util.logging.LogManager

import com.google.ortools.graph.MinCostFlow
import org.apache.log4j.LogManager
import org.apache.log4j.spi.RootLogger
import org.apache.spark.sql.SparkSession

object Main {
  System.loadLibrary("jniortools")

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().getOrCreate()
    import spark.implicits._

    val cnt = spark.createDataset(List(1, 2, 3, 4, 5, 6, 7)).map(i => {
      Thread.sleep(120000)
      runMyProgram(i, "GLOP_LINEAR_PROGRAMMING", false)
      // runTestMinCostFlow2(i)
      i
    }).count()

    println(cnt)
  }

  def runMyProgram(i: Int, solverType: String, printModel: Boolean): Unit = {
    import com.google.ortools.linearsolver.MPSolver
    val solver = new MPSolver("test", MPSolver.OptimizationProblemType.valueOf(solverType))
    val x = solver.makeNumVar(0.0, 1.0, "x")
    val y = solver.makeNumVar(0.0, 2.0, "y")
    val objective = solver.objective()
    objective.setCoefficient(y, 1)
    objective.setMaximization()
    solver.solve()
    // scalastyle:off println
    println("Solution for i = " + i)
    println("x = " + x.solutionValue())
    println("y = " + y.solutionValue())
    // scalastyle:on println

  }

  def runTestMinCostFlow1(mm : Int): Unit = {
    import com.google.ortools.graph.{MinCostFlow, MinCostFlowBase}

    val numNodes = 7
    val numArcs = 12
    val nodeIds = Array(0, 1, 2, 3, 4, 5, 6)
    val startNodes = Array(0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3)
    val endNodes = Array(4, 5, 6, 4, 5, 6, 4, 5, 6, 4, 5, 6)
    val capacities = List.fill(12) (99999999).toArray
    val unitCosts = Array(1, 2, 4, 4, 2, 1, 5, 3, 2, 7, 8, 3)

    val supplies = Array(40, 50, 60, 30, -45, -65, -70)

    val minCostFlow = new MinCostFlow()

    // Add arc
    (0 until numArcs).toList.foreach(i => {
      val arc = minCostFlow.addArcWithCapacityAndUnitCost(startNodes(i),
        endNodes(i), capacities(i), unitCosts(i))
      if (arc != i) throw new Exception("mm " + mm + "Arc cannot be added")
    })
    // Add node supplies
    (0 until numNodes).toList.foreach(i => minCostFlow.setNodeSupply(i, supplies(i)))

    // Find min cost flow
    val solveStatus = minCostFlow.solve()
    // scalastyle:off println
    println("mm " + mm + " solved - " + solveStatus)


    if (solveStatus == MinCostFlowBase.Status.OPTIMAL) {
      val optimalCost = minCostFlow.getOptimalCost
      println("mm " + mm + " Minimum cost: " + optimalCost)
      println("mm " + mm + "")
      println("mm " + mm + " Edge   Flow / Capacity  Cost")
      val i = 0
      for (i <- 0 until numArcs) {
        println("mm " + mm + " arc " + i + " w/ " + minCostFlow.getFlow(i) + " " +
          minCostFlow.getUnitCost(i) + " " + minCostFlow.getCapacity(i))

        val cost = minCostFlow.getFlow(i) * minCostFlow.getUnitCost(i)
        println("mm " + mm + " " + minCostFlow.getTail(i) + " ->" + minCostFlow.getHead(i) + " " +
          minCostFlow.getFlow(i) + " / " + minCostFlow.getCapacity(i) + " " + cost)
      }
    }

  }

  def runTestMinCostFlow2(mm : Int): Unit = {
    import com.google.ortools.graph.{MinCostFlow, MinCostFlowBase}

    val numNodes = 7
    val numArcs = 12
    val nodeIds = Array(0, 1, 2, 3, 2147483646, 2147483645, 2147483644)
    val startNodes = Array(0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3)
    val endNodes = Array(2147483646, 2147483645, 2147483644,
      2147483646, 2147483645, 2147483644,
      2147483646, 2147483645, 2147483644,
      2147483646, 2147483645, 2147483644)
    val capacities = List.fill(12) (99999999).toArray
    val unitCosts = Array(1, 2, 4, 4, 2, 1, 5, 3, 2, 7, 8, 3)

    val supplies = Array(40, 50, 60, 30, -45, -65, -70)

    val minCostFlow = new MinCostFlow()

    // Add arc
    (0 until numArcs).toList.foreach(i => {
      val arc = minCostFlow.addArcWithCapacityAndUnitCost(startNodes(i),
        endNodes(i), capacities(i), unitCosts(i))
      if (arc != i) throw new Exception("mm " + mm + "Arc cannot be added")
    })
    // Add node supplies
    (0 until numNodes).toList.foreach(i => minCostFlow.setNodeSupply(i, supplies(i)))

    // Find min cost flow
    val solveStatus = minCostFlow.solve()
    // scalastyle:off println
    println("mm " + mm + " solved - " + solveStatus)


    if (solveStatus == MinCostFlowBase.Status.OPTIMAL) {
      val optimalCost = minCostFlow.getOptimalCost
      println("mm " + mm + " Minimum cost: " + optimalCost)
      println("mm " + mm + "")
      println("mm " + mm + " Edge   Flow / Capacity  Cost")
      val i = 0
      for (i <- 0 until numArcs) {
        println("mm " + mm + " arc " + i + " w/ " + minCostFlow.getFlow(i) + " " +
          minCostFlow.getUnitCost(i) + " " + minCostFlow.getCapacity(i))

        val cost = minCostFlow.getFlow(i) * minCostFlow.getUnitCost(i)
        println("mm " + mm + " " + minCostFlow.getTail(i) + " ->" + minCostFlow.getHead(i) + " " +
          minCostFlow.getFlow(i) + " / " + minCostFlow.getCapacity(i) + " " + cost)
      }
    }

    "haha".hashCode
    val x = 10
    x.hashCode()

  }

}
