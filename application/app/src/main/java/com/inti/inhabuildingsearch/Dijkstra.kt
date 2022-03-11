import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

var n: Int = 0
var e: Int = 0
var list: ArrayList<PriorityQueue<Node>> = ArrayList<PriorityQueue<Node>>()
var distance: Array<IntArray> = arrayOf()

class Node : Comparable<Node> {
    var index = 0
    var cost = 0

    constructor(index: Int, cost: Int) {
        this.index = index
        this.cost = cost
    }

    override fun compareTo(other: Node): Int {
        return this.cost - other.cost
    }
}

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {

    val ne = readLine().split(" ")

    n = ne[0].toInt()
    e = ne[1].toInt()

    for (i in 0 until n + 1) {
        list.add(PriorityQueue<Node>())
    }

    distance = Array(3) { IntArray(n + 1) }

    for (i in 0 until e) {
        val str = readLine().split(" ")
        val a = str[0].toInt()
        val b = str[1].toInt()
        val c = str[2].toInt()

        list[a].add(Node(b, c))
        list[b].add(Node(a, c))
    }

    val vertex = readLine().split(" ")

    val node1 = vertex[0].toInt()
    val node2 = vertex[1].toInt()

    dijkstra(1, 0)
    dijkstra(node1, 1)
    dijkstra(node2, 2)

    val min = Math.min(
        distance[0][node1] + distance[1][node2] + distance[2][n],
        distance[0][node2] + distance[2][node1] + distance[1][n]
    )

    if (min >= 100000)
        println("-1")
    else
        println(min)
}

fun dijkstra(src: Int, idx: Int) {

    val pq: PriorityQueue<Node> = PriorityQueue<Node>()

    pq.add(Node(src, 0))
    Arrays.fill(distance[idx], 100000)

    var check: BooleanArray = BooleanArray(n + 1)
    Arrays.fill(check, false)

    distance[idx][src] = 0

    while (!pq.isEmpty()) {
        val now = pq.poll().index

        if (check[now]) continue
        check[now] = true

        for (node in list[now]) {
            if (distance[idx][node.index] > distance[idx][now] + node.cost) {
                distance[idx][node.index] = distance[idx][now] + node.cost
                pq.add(Node(node.index, distance[idx][node.index]))
            }
        }
    }
}

//ex.
//4 6
//1 2 3
//2 3 3
//3 4 1
//1 3 5
//2 4 5
//1 4 4
//2 3

//ex.
//5 7
//1 2 3
//1 4 2
//4 2 1
//2 5 3
//2 3 2
//4 5 5
//3 5 1
//1 5

