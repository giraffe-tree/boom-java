# union find 算法

## 思路分析

find(p): 返回 p 的连通分量标识
union(p,q): 连接两个顶点 p,q

- quick find 
    - 使用 id[] 来存储每个点对应的连通分量的id
    - find 方法直接返回 `id[p]` , 时间复杂度 O(1)
    - 但 union方法比较慢, 需要在union时, 将两个连通分量的id统一成其中一个 id
        - 这种统一需要遍历整个 id[], 所以最坏时间复杂度为 O(N)
- quick union
    - 使用 id[] 来存储每个点对应的连通分量的其中一个 id
    - 其实可以看成一棵树
    - union 的过程
        - 开始的时候, 每个点都是一棵树,每个点 p 的 `id[p]` 都是它们自己
        - 开始 union(p,q) 的时候, 就是将两棵树合并, 将一颗树的根节点作为另一颗树的 根节点
            - 我们引申出一个 root id, 即这棵树的根节点
            - 假设 `p!=id[p]` , 则 `p=id[p]`, 循环直到 `p=id[p]`, 最后的 这个 p 即为 root id (这棵树的根节点)
        - union 就是一直合并
    - find 的过程
        - 循环 `id[p]` ,直到 `p=id[p]`
        - 树的根节点即为连通分量的唯一标识
        
    - 时间复杂度
        - find: 树的高度
        - union: 树的高度
        - 树越高时间复杂度越高, 最坏为 O(N)
- weighted quick union
    - 思路就是: 基于 quick union,  记录每棵树的大小
    - 每次都把小树放入大树的根节点下
    - 时间复杂度
        - find: O(lgN)
        - union: O(lgN)
        


