package org.jdelaunay.delaunay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Delaunay Package.
 * 
 * @author Jean-Yves MARTIN, Erwan BOCHER, Adelin PIAU
 * @date 2009-01-12
 * @revision 2010-07-22
 * @version 1.3
 */

public class MyQuadTreeMapper<T extends MyElement> {
	private MyBox myBoundingBox;
	private boolean usable;
	private MyQuadTree<T> myQuadTree;
	private int maxLevel=20;//FIXME maxLevel=5 or 7 or ... ?
	private int quadtreeSize;
	

	
//	/**
//	 * Use for next() function.
//	 */
//	private String path="0";
//	private int index=0;
	
	/**
	 * Constructor
	 */
	public MyQuadTreeMapper() {
		this.myBoundingBox = null;
		this.usable = false;
		this.myQuadTree = null;
		this.quadtreeSize=0;
	}
	
	/**
	 * Constructor.
	 * Generate a empty QuadTree with a specified level.
	 * Generate all nodes.
	 * @param theBox
	 */
	public MyQuadTreeMapper(MyBox theBox) {
		this.myBoundingBox = new MyBox(theBox);
		this.usable = true;
		this.myQuadTree = new MyQuadTree<T>(maxLevel);
		this.quadtreeSize=0;
	}

	/**
	 * Constructor
	 * @param theBox
	 */
	public MyQuadTreeMapper(LinkedList<T> theList) {
		this.myBoundingBox = new MyBox();
		for (T element : theList) {
			MyBox aBox = element.getBoundingBox();
			myBoundingBox.alterBox(aBox.minx, aBox.miny, aBox.minz);
			myBoundingBox.alterBox(aBox.maxx, aBox.maxy, aBox.maxz);
		}
		this.usable = true;
		this.myQuadTree = new MyQuadTree<T>(maxLevel);
		add(theList);
		this.quadtreeSize=theList.size();
	}

	/**
	 * Constructor
	 * @param theBox
	 */
	public MyQuadTreeMapper(ArrayList<T> theList) {
		this.myBoundingBox = new MyBox();
		for (T element : theList) {
			MyBox aBox = element.getBoundingBox();
			myBoundingBox.alterBox(aBox.minx, aBox.miny, aBox.minz);
			myBoundingBox.alterBox(aBox.maxx, aBox.maxy, aBox.maxz);
		}
		this.usable = true;
		this.myQuadTree = new MyQuadTree<T>(maxLevel);
		add(theList);
		this.quadtreeSize=theList.size();
	}

	/**
	 * Tell if we can use QuadTree
	 * @return usable
	 */
	public boolean canBeUsed() {
		return this.usable;
	}

	/**
	 * Tell if we can use QuadTree
	 * @return usable
	 */
	public void cancelUsability() {
		this.usable = false;
	}
	
	/**
	 * Constructor
	 * Generate a QuadTree with a specified level and complete it with elements.
	 * Only not empty nodes are generated.
	 * @param theBox
	 */
	public MyQuadTreeMapper(MyBox theBox, ArrayList<T> elements) {
		this.myBoundingBox = new MyBox(theBox);
		this.usable = true;
		this.myQuadTree = new MyQuadTree<T>(maxLevel, myBoundingBox, elements);
		this.quadtreeSize=elements.size();
	}


	/**
	 * Set QuadTree bounding box and allow insertion / searches
	 * @param theBox
	 */
	public void setBox(MyBox theBox) {
			this.myBoundingBox = new MyBox(theBox);
			this.usable = true;
	}

	public MyBox getBox() {
		return myBoundingBox;
	}
	/**
	 * add an element in the QuadTree
	 * @param element
	 */
	public void add(T element) {
		if (this.usable) {
			MyBox theBox = element.getBoundingBox();
			myQuadTree.add(element, theBox, myBoundingBox);
			quadtreeSize++;
		}
	}
	
	
	/**
	 * add all elements in the QuadTree
	 * @param elements
	 */
	public void addAll(ArrayList<T> elements) {
		if (this.usable) {
			MyBox theBox;
			for(T element:elements)
			{
				theBox = element.getBoundingBox();
				myQuadTree.add(element, theBox, myBoundingBox);
			}
			quadtreeSize+=elements.size();
		}
		
	}
	
	
	/**
	 * add a list of elements in the QuadTree
	 * @param element
	 */
	public void add(LinkedList<T> theList) {
		if (this.usable) {
			for (T element : theList) {
				MyBox theBox = element.getBoundingBox();
				myQuadTree.add(element, theBox, myBoundingBox);
			}
			quadtreeSize+=theList.size();
		}
	}

	/**
	 * add a list of elements in the QuadTree
	 * @param element
	 */
	public void add(ArrayList<T> theList) {
		if (this.usable) {
			for (T element : theList) {
				MyBox theBox = element.getBoundingBox();
				myQuadTree.add(element, theBox, myBoundingBox);
			}
			quadtreeSize+=theList.size();
		}
	}

	/**
	 * Search the element that contains the point
	 * @param aPoint
	 * @return anElement
	 */
	public T search(MyPoint aPoint) {
		T anElement = null;
		if (this.usable) {
			anElement = myQuadTree.search(aPoint, myBoundingBox);
		}
		return anElement;
	}
	
	
	/**
	 * Search the element that contains the point
	 * @param aPoint
	 * @return anElement
	 */
	public T search(MyPoint aPoint, double precision) {
		T anElement = null;
		if (this.usable) {
			anElement = myQuadTree.search(aPoint, precision, myBoundingBox);
		}
		return anElement;
	}
	
	/**
	 * Search the element is inside the quadtree.
	 * @param element
	 * @return True if it is.
	 */
	public boolean contains(T element) {
		if (this.usable) {
			return myQuadTree.contains(element, myBoundingBox);
		}
		return false;
	}
	
	
	/**
	 * Remove data from the QuadTree
	 */
	protected void removeData() {
		if (this.usable) {
			myQuadTree.removeData();
			quadtreeSize=0;
		}
	}

	/**
	 * Redefine QuadTree
	 * @param theBox
	 */
	protected void remap(MyBox theBox) {
		if (this.usable) {
			this.myBoundingBox = new MyBox(theBox);
			this.removeData();
		}
	}
	
	/**
	 * Search the element that have th gid
	 * @param gid
	 * @return anElement
	 */
	public T searchGID(int gid) {
		T anElement = null;
		if (this.usable) {
			anElement = myQuadTree.searchGID(gid, myBoundingBox);
		}
		return anElement;
	}
	
	/**
	 * Search the element that contains the point
	 * @param aPoint
	 * @return anElement
	 */
	public T search(double x, double y) {
		T anElement = null;
		if (this.usable) {
			anElement = myQuadTree.search(new MyPoint(x,y), myBoundingBox);
		}
		return anElement;
	}
	
	/**
	 * Search the element that contains the point
	 * @param aPoint
	 * @return anElement
	 */
	public T search(double x, double y, double z) {
		T anElement = null;
		if (this.usable) {
			anElement = myQuadTree.search(new MyPoint(x,y, z), myBoundingBox);
		}
		return anElement;
	}
	
	
	/**
	 * @param anEdge
	 * @param searchBoundingBox Bounding box of anEdge.
	 * @param boundingBox
	 * @return ArrayList of Object[].<br/>
	 * Object[0] is an MyEdge<br/>
	 * Object[1] is an Integer ( The result of anEdge.intersects(...) ).
	 */
	public ArrayList<Object[]> searchIntersection(MyEdge anEdge) {
		ArrayList<Object[]>  allElements = null;
		if (this.usable) {
			allElements = myQuadTree.searchIntersection(anEdge, anEdge.getBoundingBox(), myBoundingBox);
		}
		return allElements;
	}
	
	
	/**
	 * Search all elements inside or on the area searchBoundingBox.
	 * @param searchBoundingBox Area of search.
	 * @return All elements inside or on the area searchBoundingBox.
	 */
	public ArrayList<T> searchAll(MyBox searchBoundingBox) {
		ArrayList<T>  allElements = null;
		if (this.usable) {
			allElements = myQuadTree.searchAll(searchBoundingBox, myBoundingBox);
		}
		return allElements;
	}
	
	
	/**
	 * Search all elements strictly inside the area searchBoundingBox.
	 * @param searchBoundingBox Area of search.
	 * @return All elements strictly inside the area searchBoundingBox.
	 */
	public ArrayList<T> searchAllStric(MyBox searchBoundingBox) {
		ArrayList<T>  allElements = null;
		if (this.usable) {
			allElements = myQuadTree.searchAllStric(searchBoundingBox, myBoundingBox);
		}
		return allElements;
	}
	
	
	/**
	 * Search in which element middle of anElement is.
	 * @param <E>
	 * @param anElement
	 * @return The element that contain anElement.
	 */
	public <E extends MyElement> T searchInWhichElementItIs(E anElement) {
		if (this.usable) {
		return myQuadTree.searchInWhichElementItIs(anElement, myBoundingBox);
		}
		return null;
	}
	
	/**
	 * Remove element.
	 * @param element
	 * @return true if it remove.
	 */
	public boolean remove(T element) {
		if (this.usable) {
			return quadtreeSize!=(quadtreeSize-=myQuadTree.remove(element, myBoundingBox));
		}
		return false;
	}
	
	
	/**
	 * Remove all elements strictly inside the polygon's area.
	 * @param aPolygon Area of search.
	 * @return All elements in the quadtree with out elements strictly inside the area searchBoundingBox.
	 */
	public void removeAllStric(MyPolygon aPolygon) {
		if (this.usable) {
			quadtreeSize -= myQuadTree.removeAllStric(aPolygon, myBoundingBox);
		}
	}
	

	
	/**
	 * @return All element in the quadtree.
	 */
	public ArrayList<T> getAll()
	{
		if (this.usable) {
			return myQuadTree.getAll();
		}
		return new ArrayList<T>();
	}
	
	
	/**
	 * @return number of elements in the quadtree.
	 */
	public int size()
	{
		return quadtreeSize;
	}

	/**
	 * @return True if quadtree is empty.
	 */
	public boolean isEmpty() {
		return quadtreeSize<=0;
	}

	
	/**
	 * Set gid to all elements in the quadtree.
	 */
	public void setAllGIDs() {
		if (this.usable) {
			
			int gid=1;
			for(T a:myQuadTree.getAll())
			{
				a.setGID(gid);
				gid++;
			}
		}
		
	}
}
