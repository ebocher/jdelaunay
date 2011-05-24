/*
 * jDelaunay is a library dedicated to the processing of Delaunay and constrained 
 * Delaunay triangulations from PSLG inputs.
 * 
 * This library is developed at French IRSTV institute as part of the AvuPur and Eval-PDU project, 
 * funded by the French Agence Nationale de la Recherche (ANR) under contract 
 * ANR-07-VULN-01 and ANR-08-VILL-0005-01 .
 * 
 * jDelaunay is distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 * 
 * Copyright (C) 2010 Erwan BOCHER, Alexis GUEGANNO, Adelin PIAU, Jean-Yves MARTIN
 * Copyright (C) 2011 Alexis GUEGANNO, Jean-Yves MARTIN
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
 * For more information, please consult: <http://trac.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.jdelaunay.delaunay.evaluator;

import junit.framework.TestCase;

/**
 * Class used to test the skinny evaluator.
 * @author alexis
 */
public class TestSkinnyEvaluator extends TestCase{
        
        public void testInstanciationGetSet(){
                SkinnyEvaluator se = new SkinnyEvaluator(20);
                assertTrue(se.getMinAngle() == 20);
                se.setMinAngle(15);
                assertTrue(se.getMinAngle() == 15);
        }     
        
        public void testThresholdValueException(){
                try{
                        SkinnyEvaluator se = new SkinnyEvaluator(33);
                        assertTrue(false);
                } catch (IllegalArgumentException e){
                        assertTrue(true);
                }
                try{
                        SkinnyEvaluator se = new SkinnyEvaluator(-1);
                        assertTrue(false);
                } catch (IllegalArgumentException e){
                        assertTrue(true);
                }
                SkinnyEvaluator se = new SkinnyEvaluator(20);
                try {
                        se.setMinAngle(33);
                        assertTrue(false);
                } catch (IllegalArgumentException e) {
                        assertTrue(true);
                }
                try {
                        se.setMinAngle(-1);
                        assertTrue(false);
                } catch (IllegalArgumentException e) {
                        assertTrue(true);
                }
        }
}
