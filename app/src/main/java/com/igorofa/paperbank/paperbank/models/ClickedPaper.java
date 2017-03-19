package com.igorofa.paperbank.paperbank.models;

public class ClickedPaper {
        int positionInAdapter;
        Paper mPaper;

        public ClickedPaper(int positionInAdapter){
            this.positionInAdapter = positionInAdapter;
        }

        public ClickedPaper(int positionInAdapter, Paper paper){
            this.positionInAdapter = positionInAdapter;
            this.mPaper = paper;
        }

        public int getPositionInAdapter() {
            return positionInAdapter;
        }

        public Paper getPaper() {
            return mPaper;
        }
    }