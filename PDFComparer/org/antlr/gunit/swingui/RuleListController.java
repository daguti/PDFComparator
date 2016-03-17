/*     */ package org.antlr.gunit.swingui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.event.ListDataListener;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import org.antlr.gunit.swingui.model.Rule;
/*     */ import org.antlr.gunit.swingui.model.TestSuite;
/*     */ 
/*     */ public class RuleListController
/*     */   implements IController
/*     */ {
/*  54 */   private final JList list = new JList();
/*  55 */   private final JScrollPane scroll = new JScrollPane(this.list, 22, 30);
/*     */ 
/*  60 */   private ListModel model = null;
/*  61 */   private TestSuite testSuite = null;
/*     */ 
/*     */   public RuleListController() {
/*  64 */     initComponents();
/*     */   }
/*     */ 
/*     */   public JScrollPane getView() {
/*  68 */     return this.scroll;
/*     */   }
/*     */ 
/*     */   private void setTestSuite(TestSuite newTestSuite) {
/*  72 */     this.testSuite = newTestSuite;
/*  73 */     this.model = new RuleListModel();
/*  74 */     this.list.setModel(this.model);
/*     */   }
/*     */ 
/*     */   public void initialize(TestSuite ts) {
/*  78 */     setTestSuite(ts);
/*  79 */     if (this.model.getSize() > 0) this.list.setSelectedIndex(0);
/*  80 */     this.list.updateUI();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  89 */     this.scroll.setViewportBorder(BorderFactory.createEtchedBorder());
/*  90 */     this.scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Rules"));
/*     */ 
/*  92 */     this.scroll.setOpaque(false);
/*     */ 
/*  94 */     this.list.setOpaque(false);
/*  95 */     this.list.setSelectionMode(1);
/*  96 */     this.list.setLayoutOrientation(0);
/*  97 */     this.list.setCellRenderer(new RuleListItemRenderer());
/*     */   }
/*     */ 
/*     */   public void setListSelectionListener(ListSelectionListener l) {
/* 101 */     this.list.addListSelectionListener(l);
/*     */   }
/*     */ 
/*     */   public Object getModel() {
/* 105 */     return this.model;
/*     */   }
/*     */ 
/*     */   public Component getView()
/*     */   {
/*  51 */     return getView();
/*     */   }
/*     */ 
/*     */   private class RuleListModel
/*     */     implements ListModel
/*     */   {
/*     */     public RuleListModel()
/*     */     {
/* 145 */       if (RuleListController.this.testSuite == null)
/* 146 */         throw new NullPointerException("Null test suite");
/*     */     }
/*     */ 
/*     */     public int getSize() {
/* 150 */       return RuleListController.this.testSuite.getRuleCount();
/*     */     }
/*     */ 
/*     */     public Object getElementAt(int index) {
/* 154 */       return RuleListController.this.testSuite.getRule(index);
/*     */     }
/*     */ 
/*     */     public void addListDataListener(ListDataListener l)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void removeListDataListener(ListDataListener l)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private class RuleListItemRenderer extends JLabel
/*     */     implements ListCellRenderer
/*     */   {
/*     */     public RuleListItemRenderer()
/*     */     {
/* 114 */       setPreferredSize(new Dimension(50, 18));
/*     */     }
/*     */ 
/*     */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus)
/*     */     {
/* 121 */       if ((value instanceof Rule)) {
/* 122 */         Rule item = (Rule)value;
/* 123 */         setText(item.toString());
/* 124 */         setForeground(list.getForeground());
/*     */ 
/* 126 */         setIcon(item.getNotEmpty() ? ImageFactory.getSingleton().FAV16 : null);
/*     */ 
/* 128 */         if (list.getSelectedValue() == item) {
/* 129 */           setBackground(Color.LIGHT_GRAY);
/* 130 */           setOpaque(true);
/*     */         } else {
/* 132 */           setOpaque(false);
/*     */         }
/*     */       }
/*     */       else {
/* 136 */         setText("Error!");
/*     */       }
/* 138 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.RuleListController
 * JD-Core Version:    0.6.2
 */