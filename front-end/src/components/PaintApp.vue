<template>
    <div>
        <div class="panel"
        v-bind:style="{ left: `${x}px`, top: `${y}px` }"
        @mousedown="startDrag"
        @mousemove="dragging"
        @mouseup="stopDrag"
        @mouseleave="stopDrag">
            <div class="commands">
                <button> load </button>
                <button> save</button>
                <button> undo </button>
                <button> redo </button>
            </div>
            <div class="shapes">
                <button> ◌ </button>
                <button> ◻</button>
                <button> ☖ </button>
                <button> / </button>
            </div>
            <div class="colors">
                <button> r </button>
                <button> b </button>
                <button> y </button>
                <button> g </button>
                <button> o </button>
                <button> c </button>
                <button> m </button>
                <button> k </button>
                <button class="customcolor"> custom color </button>
            </div>
        </div>

    <div class="wrapper">
        <div class="canvas" :style="{ width: `${CW}px`, height: `${CH}px` }"> </div>
    </div>

    </div>
</template>

<script>


export default {
    data(){
        return{
            CW: 1200,
            CH: 600,
            isDragging: false,
            x: 0,
            y: 0,
            startX: 0,
            startY: 0,
        };
    },
    methods: {
        startDrag(event) {
            this.isDragging = true;
            this.startX = event.clientX - this.x;
            this.startY = event.clientY - this.y;
        },
        dragging(event) {
            if (this.isDragging) {
                this.x = event.clientX - this.startX;
                this.y = event.clientY - this.startY;
        }
        },
        stopDrag() {
            this.isDragging = false;
        },
    },
}
</script>

<style scoped>
    .wrapper{
        position: absolute;
        height: 100vh;
        width: 100vw;
        background: #ffa31a;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .canvas{
        background-color: white;
        border: 3px solid #1b1b1b;
        border-radius: 10px;
    }
    .panel{
        position: absolute;
        display: flex;
        z-index: 5;
        padding: 15px;
        background: #808080;
        gap: 20px;
        border-radius: 5px;
        cursor: move;
    }

    .shapes {
        display: grid;
        grid-template-rows: repeat(2, 1fr);
        gap: 5px;
        grid-auto-flow: column;
        
    }
    .colors {
        display: grid;
        grid-template-rows: repeat(2, 1fr);
        gap: 5px;
        grid-auto-flow: column;
    }
    .commands {
        display: grid;
        grid-template-rows: repeat(2, 1fr);
        gap: 5px;
        grid-auto-flow: column;
    }

    button{
        width: 50px;
        height: 30px;
    }
    .customcolor{
        grid-column: span 2;
        grid-row: span 2;
        width: auto;
        height: auto;
    }
</style>
