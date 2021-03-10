# 背包问题

## 思路


```python
/* 
 * tn: traversed n，即已经遍历过的物品；
 * rw: reserved w，即背包还能容量的重量。
 */
DP(int tn, int rw) {
  // 当遍历完所有物品时，就该返回 0 了，因为没有物品也就没有价值了
  if tn < 0
    return 0
  
  // 当背包还能容纳的重量已经小于当前物品的重量时，显然这个物品不能放入背包
  if rw < w[tn]
    return DP(tn - 1, rw)
  
  // 作出决策，该不该放入物品：
  //   1. 放入：那么价值是 DP(tn - 1, rw - w[tn])；
  //   2. 不放入：那么价值是 DP(tn - 1, rw)。
  return max(DP(tn - 1, rw), DP(tn - 1, rw - w[tn]) + v[tn])
}
```




