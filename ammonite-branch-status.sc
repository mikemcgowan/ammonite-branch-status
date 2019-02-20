import ammonite.ops._
import ammonite.ops.ImplicitWd._

val refsHeads  = "refs/heads/"
val forEachRef = %%git("for-each-ref", "--format='%(refname)'", refsHeads)
val revList    = (b: String) => %%git("rev-list", "--left-right", "--count", "master..." + b)
val f          = (b: String) => b -> revList(b).out.lines(0).split('\t').map(_.toInt)
val branches   = forEachRef.out.lines.map(_.tail.init.drop(refsHeads.length))
val results    = branches map f

results.foreach(r => printf("%s: %s behind, %s ahead%n", r._1, r._2(0), r._2(1)))
