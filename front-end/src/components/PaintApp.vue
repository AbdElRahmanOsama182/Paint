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
                <button> undo </button>
                <button> save</button>
                <button> redo </button>
            </div>
            <div class="shapes">
                <button> ◌ </button>
                <button> ◻</button>
                <button> ☖ </button>
                <button> / </button>
            </div>
            <div class="colors">
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
                <button> </button>
            </div>
            <button class="customcolor"> custom color </button>
        </div>

    <div class="wrapper">
        <div class="canvas" :style="{ width: `${CW}px`, height: `${CH}px`}"></div>
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
            x: 450,
            y: 550,
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
        background: lightblue;
        display: flex;
        justify-content: center;
        align-items: center;
        top: 0px;left: 0px;bottom: 0px;right: 0px;
    }
    .canvas{
        background-color: white;
        border-radius: 10px;
        border: 0px solid;
        box-shadow: 3px 3px 10px 0px rgba(0, 0, 0, 0.1);
    }
    .panel{
        position: absolute;
        display: flex;
        z-index: 5;
        padding: 15px;
        padding-right: 50px;
        background: #80808041;
        gap: 20px;
        border-radius: 5px;
        cursor: move;
        backdrop-filter: blur(5px);
    }

    button{
        cursor: pointer;
        border: 0px;
        background: white;
        border-radius: 5px;
        transition: all 100ms ease-out;
    }
    button:hover{
        opacity: 70%;
    }
    button:active{
        scale: 110%;
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

.colors button {
    width: 30px;
    border-radius: 50px;
    border: 0;
    box-shadow: inset 2px 2px 0px rgba(0, 0, 0, 0.2);
}
.colors button:nth-child(1) {background-color: black;}
.colors button:nth-child(2) {background-color: white;}
.colors button:nth-child(3) {background-color: cyan;}
.colors button:nth-child(4) {background-color: blue;}
.colors button:nth-child(5) {background-color: yellow;}
.colors button:nth-child(6) {background-color: green;}
.colors button:nth-child(7) {background-color: magenta;}
.colors button:nth-child(8) {background-color: red;}
.customcolor{
    grid-column: span 2;
    grid-row: span 2;
    width: 60px;
    height: auto;
}

</style>
