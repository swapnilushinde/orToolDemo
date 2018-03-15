import org.apache.spark.sql.SparkSession

object Main {
  System.loadLibrary("jniortools")

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().getOrCreate()
    import spark.implicits._

    spark.createDataset(List(1, 2, 3, 4, 5, 6, 7)).foreach(i => {
      runMyProgram(i, "GLOP_LINEAR_PROGRAMMING", false)
    })
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

}
