package dev.aybarsacar.datastructures.unionfind;

/**
 * Union find data structure with path compression
 * NOTE: use bijection to map the values in the union find to the elements in your program
 */
public class UnionFind
{
  private int size;                 // The number of elements in this union find
  private int[] sz;                 // Used to track the size of each of the component
  private int[] id;                 // id[i] points to the parent of i, if id[i] = i then i is a root node
  private int numComponents;        // Tracks the number of components in the union find

  public UnionFind(int size)
  {
    if (size <= 0) throw new IllegalArgumentException("Size cannot be less than or equal to zero");

    this.size = numComponents = size;
    sz = new int[size];
    id = new int[size];

    for (int i = 0; i < size; i++)
    {
      id[i] = i;      // Link each node to itself
      sz[i] = 1;      // Each component is originally of size 1
    }
  }

  /**
   * find which component / set 'p' belongs to
   * it does path compression along the way
   * Amortised constant time
   *
   * @param p
   * @return
   */
  public int find(int p)
  {
//    find the root of the component
    int root = p;
    while (root != id[root]) root = id[root];

//    Compress the path leading back to the root
//    Path compression
    while (p != root)
    {
      int next = id[p];
      id[p] = root;
      p = next;
    }

    return root;
  }

  /**
   * returns whether elements p and q are in the same component
   *
   * @param p
   * @param q
   * @return
   */
  public boolean connected(int p, int q)
  {
    return find(p) == find(q);
  }

  /**
   * returns the size of the component which p belongs to
   *
   * @param p
   * @return
   */
  public int componentSize(int p)
  {
    return sz[find(p)];
  }

  public int size()
  {
    return size;
  }

  /**
   * @return the number of remaining components
   */
  public int components()
  {
    return numComponents;
  }

  /**
   * Unifies the components containing elements p and q
   *
   * @param p
   * @param q
   */
  public void unify(int p, int q)
  {
    int root1 = find(p);
    int root2 = find(q);

//    if the elements in already in the same group return to avoid cycles
    if (root1 == root2) return;

//    merge the smaller component to the larger one
    if (sz[root1] < sz[root2])
    {
      sz[root2] += sz[root1];
      id[root1] = root2;
    }
    else
    {
      sz[root1] += sz[root2];
      id[root2] = root1;
    }

    numComponents--;
  }
}
