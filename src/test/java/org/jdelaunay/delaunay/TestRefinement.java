/**
 *
 * jDelaunay is a library dedicated to the processing of Delaunay and constrained
 * Delaunay triangulations from PSLG inputs.
 *
 * This library is developed at French IRSTV institute as part of the AvuPur and Eval-PDU project,
 * funded by the French Agence Nationale de la Recherche (ANR) under contract
 * ANR-07-VULN-01 and ANR-08-VILL-0005-01 .
 *
 * jDelaunay is distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2010-2012 IRSTV FR CNRS 2488
 *
 * jDelaunay is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * jDelaunay is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * jDelaunay. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.jdelaunay.delaunay;

import java.util.Collections;
import java.util.List;
import org.jdelaunay.delaunay.evaluator.SkinnyEvaluator;
import org.jdelaunay.delaunay.error.DelaunayError;
import org.jdelaunay.delaunay.evaluator.InsertionEvaluator;
import org.jdelaunay.delaunay.evaluator.TriangleQuality;
import org.jdelaunay.delaunay.geometries.DEdge;
import org.jdelaunay.delaunay.geometries.DPoint;
import org.jdelaunay.delaunay.geometries.DTriangle;

/**
 * This class gathers some tests related to the mesh refinement.
 * @author Alexis Guéganno
 */
public class TestRefinement extends BaseUtility {
        
        
        public void testRefinementThreeConstraints() throws DelaunayError {
		ConstrainedMesh mesh = new ConstrainedMesh();
		DEdge constr = new DEdge(4,0,0,4,6,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(7,6,0,3,7,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(0,2,0,3,7,0);
		mesh.addConstraintEdge(constr);
		mesh.processDelaunay();
                List<DEdge> edges = mesh.getEdges();
                SkinnyEvaluator se = new SkinnyEvaluator(15);
                mesh.refineMesh(0.01, se);
//                show(mesh);
                List<DEdge> cons = mesh.getConstraintEdges();
                edges = mesh.getEdges();
                for(DEdge ed : cons){
                        int ind = edges.indexOf(ed);
                        //To avoid any surprise about references, we make an aggressive
                        //check here
                        assertTrue(ind>=0);
                        assertTrue(ed == edges.get(ind));
                }
                assertTrue(cons.size()==7);
                assertTrue(cons.contains(new DEdge(0,2,0,1.5,4.5,0)));
                assertTrue(cons.contains(new DEdge(1.5,4.5,0,3,7,0)));
                assertTrue(cons.contains(new DEdge(3,7,0,4,6.75,0)));
                assertTrue(cons.contains(new DEdge(4,6.75,0,5,6.5,0)));
                assertTrue(cons.contains(new DEdge(5,6.5,0,7,6,0)));
                assertTrue(cons.contains(new DEdge(4,0,0,4,3,0)));
                assertTrue(cons.contains(new DEdge(4,3,0,4,6,0)));
                List<DTriangle> tris = mesh.getTriangleList();
                assertTrue(tris.size()==10);
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(0,2,0,4,0,0), 
                                new DEdge(4,0,0,4,3,0), 
                                new DEdge(4,3,0,0,2,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(0,2,0,4,3,0), 
                                new DEdge(4,3,0,1.5,4.5,0), 
                                new DEdge(1.5,4.5,0,0,2,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(4,6,0,4,3,0), 
                                new DEdge(4,3,0,1.5,4.5,0), 
                                new DEdge(1.5,4.5,0,4,6,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(4,6,0,3,7,0), 
                                new DEdge(3,7,0,1.5,4.5,0), 
                                new DEdge(1.5,4.5,0,4,6,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(4,6,0,3,7,0), 
                                new DEdge(3,7,0,4,6.75,0), 
                                new DEdge(4,6.75,0,4,6,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(4,6,0,5,6.5,0), 
                                new DEdge(5,6.5,0,4,6.75,0), 
                                new DEdge(4,6.75,0,4,6,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(4,6,0,5,6.5,0), 
                                new DEdge(5,6.5,0,5.5,3,0), 
                                new DEdge(5.5,3,0,4,6,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(7,6,0,5,6.5,0), 
                                new DEdge(5,6.5,0,5.5,3,0), 
                                new DEdge(5.5,3,0,7,6,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(4,6,0,4,3,0), 
                                new DEdge(4,3,0,5.5,3,0), 
                                new DEdge(5.5,3,0,4,6,0))));
                assertTrue(tris.contains(new DTriangle(
                                new DEdge(4,0,0,4,3,0), 
                                new DEdge(4,3,0,5.5,3,0), 
                                new DEdge(5.5,3,0,4,0,0))));
                assertCoherence(mesh);
                assertGIDUnicity(mesh);
                assertTrianglesTopology(mesh);
        }
        
        public void testRefineManyConstraints() throws DelaunayError {
		ConstrainedMesh mesh = new ConstrainedMesh();
		DEdge constr = new DEdge(0,3,0,8,3,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(9,0,0,9,6,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(12,6,0,8,7,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(5,4,0,8,7,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(12,6,0,12,7,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(8,3,0,9,6,0);
		mesh.addConstraintEdge(constr);
		constr = new DEdge(8,7,0,12,12,0);
		mesh.addConstraintEdge(constr);
		mesh.addPoint(new DPoint(4,5,0));
		mesh.addPoint(new DPoint(4,1,0));
		mesh.addPoint(new DPoint(10,3,0));
		mesh.addPoint(new DPoint(11,9,0));
		mesh.processDelaunay();
                SkinnyEvaluator se = new SkinnyEvaluator(14);
                mesh.refineMesh(1, se);
//                show(mesh);
                assertTrue(true);
                assertTrianglesTopology(mesh);
                
                
        }
        
        /**
         * Tests that we throw the wanted exception when handling the refinement methods.
         * @throws DelaunayError 
         */
        public void testRefinementException() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addPoint(new DPoint(0,0,0));
                mesh.addPoint(new DPoint(3,0,0));
                mesh.addPoint(new DPoint(0,3,0));
                mesh.processDelaunay();
                SkinnyEvaluator se = new SkinnyEvaluator(15);
                try {
                        mesh.refineMesh(-20, se);
                        assertTrue(false);
                } catch (IllegalArgumentException e) {
                        assertTrue(true);
                }
                
        }
        
        /**
         * When adding a point during the refinement operation, we must be sure to
         * obtain, as the z-coordinate of our new point, the interpolation of a value made
         * in the triangle that contains the point. If this triangle is different 
         * fomr the triangle that has this point as its circumcenter, we definitely
         * must not keep the z-coordinate of the circumcenter.
         * In this test, the circumcenter of the triangle we want to refine is 
         * (5,4). Its z-coordinate must be 0, and not -15 (which is the original
         * z coordinate of this point as the circumcenter of the triangle
         * (0 4 10, 2 0 0, 2 8 0)
         * @throws DelaunayError 
         */
        public void testRefinemenHeight() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addPoint(new DPoint(0,4,10));
                mesh.addPoint(new DPoint(2,8,0));
                mesh.addPoint(new DPoint(2,0,0));
                mesh.addPoint(new DPoint(11,4,0));
                mesh.processDelaunay();
                int index = mesh.getTriangleList().indexOf(new DTriangle(new DPoint(0,4,10),new DPoint(2,0,0),new DPoint(2,8,0)));
                DTriangle tri = mesh.getTriangleList().get(index);
                mesh.insertTriangleCircumCenter(tri, false, 0.2);
                assertFalse(mesh.getPoints().contains(new DPoint(5,4,-15)));
                assertTrue(mesh.getPoints().contains(new DPoint(5,4,0)));
                assertTrianglesTopology(mesh);
                
        }
        
        /**
         * The same goal as in testRefinementHeight, but in this case the point is on
         * an edge of the mesh.
         * @throws DelaunayError 
         */
        
        public void testRefinementHeightBis() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addPoint(new DPoint(0,2,0));
                mesh.addPoint(new DPoint(5,2,0));
                mesh.addPoint(new DPoint(2,4,10));
                mesh.addPoint(new DPoint(2,0,0));
                mesh.processDelaunay();
                assertTrianglesTopology(mesh);
                int index = mesh.getTriangleList().indexOf(new DTriangle(new DPoint(2,4,10),new DPoint(2,0,0),new DPoint(0,2,0)));
                DTriangle tri = mesh.getTriangleList().get(index);
                mesh.insertTriangleCircumCenter(tri, false, 0.2);
                assertTrue(mesh.getPoints().contains(new DPoint(2,2,5)));
//                show(mesh);
                assertTrianglesTopology(mesh);
        }
        
        /**
         * A configuration from the contour line of the Chezine river. A NullPointerException
         * was thrown, due to a mismanagement of the edge-triangles association
         * during the triangle refinement.
         * @throws DelaunayError 
         */
        public void testRefinementChezine() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                //Upper part.
                mesh.addConstraintEdge(new DEdge (      0, 19, 0, 
                                                        2, 20, 0));
                mesh.addConstraintEdge(new DEdge (      2, 20, 0, 
                                                        6, 23, 0));
                //lower part.
                mesh.addConstraintEdge(new DEdge (      3, 0, 0, 
                                                        6, 3, 0));
                mesh.addConstraintEdge(new DEdge (      6, 3, 0,  
                                                        12, 4, 0));
                mesh.processDelaunay();
                mesh.refineMesh(1, new SkinnyEvaluator(15));
                Collections.sort(mesh.getTriangleList());
//                show(mesh);
                assertCoherence(mesh);
                assertTrianglesTopology(mesh);
        }
        
        /**
         * A configuration from the contour line of the Chezine river. A NPE
         * was thrown, due to a mismanagement of the data structures while trying to add
         * a point on an existing edge of the mesh. This has been fixed, by ensuring we
         * associate well the new edges to their triangles.
         * @throws DelaunayError 
         */
        public void testRefinementChezine2() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addConstraintEdge(new DEdge (0,  0,   0, 7,  9,  0));
                mesh.addConstraintEdge(new DEdge (7,  9,   0, 22, 20, 0));
                mesh.addConstraintEdge(new DEdge (7,  59,  0, 14, 55, 0));
//                mesh.addConstraintEdge(new DEdge (12,60,  0, 14, 55, 0));
                mesh.processDelaunay();
                mesh.edgeSplitting(1);
                assertTrianglesTopology(mesh);
//                show(mesh);
                mesh.refineMesh(1, new SkinnyEvaluator(20));
                assertCoherence(mesh);
                assertTrianglesTopology(mesh);
                
        }
        
        /**
         * A configuration from the contour line of the Chezine river.
         * @throws DelaunayError 
         */
        public void testRefinementChezine3() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addConstraintEdge(new DEdge (0,  142, 0, 10, 143, 0));
                mesh.addConstraintEdge(new DEdge (10,  18, 0, 20,   0, 0));
                mesh.addConstraintEdge(new DEdge (10, 143, 0, 20, 149, 0));
                mesh.processDelaunay();
                assertTrianglesTopology(mesh);
//                show(mesh);
                mesh.refineMesh(.5, new SkinnyEvaluator(15));
                assertCoherence(mesh);
                assertTrianglesTopology(mesh);
        }
        
        
        /**
         * A stack overflow was thrown when processing this configuration. This was due 
         * to a mismanagement of the triangles during swap operations. In some cases,
         * the triangles were elected for swapping, but were in a configuration that 
         * forbids this operation. In this case, this finally led to a StackOverflow,
         * due to an infinite recursive call when trying to locate a point in the mesh.
         * @throws DelaunayError 
         */
        public void testRefinementChezine4() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addConstraintEdge(new DEdge (160.0, 89.16132207820192, 0.0, 192.72714662225917, 104.0, 0.0));
                mesh.addConstraintEdge(new DEdge (180.0, 312.8887946992181, 0.0, 220.0, 340.1904185237363, 0.0));
                mesh.addConstraintEdge(new DEdge (280.0, 143.28555858321488, 0.0, 320.0, 164.0, 0.0));
                mesh.forceConstraintIntegrity();
                mesh.processDelaunay();
                mesh.refineMesh(1, new SkinnyEvaluator(15));
                assertTrianglesTopology(mesh);
                assertCoherence(mesh);
        }
        
        public void testRefinementChezine5() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addConstraintEdge(new DEdge (0.0, 284.0, 0.0, 20.0, 286.6317057502456, 0.0));
                mesh.addConstraintEdge(new DEdge (20.0, 36.340432439465076, 0.0, 40.0, 0.8422320676036179, 0.0));
                mesh.addConstraintEdge(new DEdge (80.0, 308.9055680311285, 0.0, 100.0, 306.72714662225917, 0.0));
                mesh.addConstraintEdge(new DEdge (120.0, 64.0, 0.0, 140.0, 89.94593479996547, 0.0));
                mesh.addConstraintEdge(new DEdge (160.0, 89.16132207820192, 0.0, 192.72714662225917, 104.0, 0.0));
                mesh.forceConstraintIntegrity();
                mesh.processDelaunay();
//                show(mesh);
                mesh.refineMesh(1, new SkinnyEvaluator(15));
                assertTrianglesTopology(mesh);
                assertCoherence(mesh);
        }
        
        public void testSafeRefinement() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addPoint(new DPoint(0,6,0));
                mesh.addPoint(new DPoint(2,9,0));
                mesh.addPoint(new DPoint(3,0,0));
                mesh.addPoint(new DPoint(11,8,0));
                mesh.processDelaunay();
                assertTrue(mesh.getTriangleList().size()==2);
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(0,6,0),new DPoint(2,9,0),new DPoint(3,0,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(11,8,0),new DPoint(2,9,0),new DPoint(3,0,0))));
                InsertionEvaluator ie = new SkinnyEvaluator(25);
                int index = mesh.getTriangleList().indexOf(new DTriangle(new DPoint(0,6,0),new DPoint(2,9,0),new DPoint(3,0,0)));
                DTriangle tri = mesh.getTriangleList().get(index);
                assertTrue(ie.evaluate(tri));
                mesh.refineTriangles(0.5, ie);
                assertTrue(mesh.getTriangleList().size()==2);
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(0,6,0),new DPoint(2,9,0),new DPoint(3,0,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(11,8,0),new DPoint(2,9,0),new DPoint(3,0,0))));
        }
        
        public void testSafeRefinementSucceed() throws DelaunayError {
                ConstrainedMesh mesh = new ConstrainedMesh();
                mesh.addPoint(new DPoint(0,6,0));
                mesh.addPoint(new DPoint(2,9,0));
                mesh.addPoint(new DPoint(3,0,0));
                mesh.addPoint(new DPoint(7,13,0));
                mesh.addPoint(new DPoint(11,-1,0));
                mesh.addPoint(new DPoint(11,8,0));
                mesh.processDelaunay();
                assertTrue(mesh.getTriangleList().size()==4);
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(0,6,0),new DPoint(2,9,0),new DPoint(3,0,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(11,8,0),new DPoint(2,9,0),new DPoint(3,0,0))));
                InsertionEvaluator ie = new SkinnyEvaluator(25);
                int index = mesh.getTriangleList().indexOf(new DTriangle(new DPoint(0,6,0),new DPoint(2,9,0),new DPoint(3,0,0)));
                DTriangle tri = mesh.getTriangleList().get(index);
                DPoint cc = new DPoint(tri.getCircumCenter());
                assertTrue(ie.evaluate(tri));
                mesh.refineTriangles(0.5, ie);
                assertTrue(mesh.getTriangleList().size()==6);
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(0,6,0),cc,new DPoint(3,0,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(0,6,0),cc,new DPoint(2,9,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(7,13,0),cc,new DPoint(2,9,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(7,13,0),cc,new DPoint(11,8,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(11,-1,0),cc,new DPoint(11,8,0))));
                assertTrue(mesh.getTriangleList().contains(new DTriangle(new DPoint(11,-1,0),cc,new DPoint(3,0,0))));
                
        }

    public void testRefinementQuality() throws DelaunayError {
        ConstrainedMesh mesh = new ConstrainedMesh();
        DEdge constr = new DEdge(0,3,0,8,3,0);
        mesh.addConstraintEdge(constr);
        constr = new DEdge(9,0,0,9,6,0);
        mesh.addConstraintEdge(constr);
        constr = new DEdge(12,6,0,8,7,0);
        mesh.addConstraintEdge(constr);
        constr = new DEdge(5,4,0,8,7,0);
        mesh.addConstraintEdge(constr);
        constr = new DEdge(12,6,0,12,7,0);
        mesh.addConstraintEdge(constr);
        constr = new DEdge(8,3,0,9,6,0);
        mesh.addConstraintEdge(constr);
        constr = new DEdge(8,7,0,12,12,0);
        mesh.addConstraintEdge(constr);
        mesh.addPoint(new DPoint(4,5,0));
        mesh.addPoint(new DPoint(4,1,0));
        mesh.addPoint(new DPoint(10,3,0));
        mesh.addPoint(new DPoint(11,9,0));
        mesh.processDelaunay();
        TriangleQuality se = new TriangleQuality();
        List<DTriangle> triangles = mesh.getTriangleList();
        assertEquals(18, triangles.size());
        double sumArea = 0;
        for(DTriangle triangle : triangles) {
            sumArea+=triangle.getArea();
        }
        mesh.refineMesh(1, se);
        assertTrianglesTopology(mesh);
        triangles = mesh.getTriangleList();
        assertEquals(54, triangles.size());
        double refineArea = 0;
        for(DTriangle triangle : triangles) {
            refineArea+=triangle.getArea();
        }
        assertEquals(sumArea, refineArea, 1e-12);
    }
}
